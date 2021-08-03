package com.codepath.myrecipes.ui.compose;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.ui.openingScreen.MainActivity;
import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    public static final String TAG = "DashboardFragment";
    final int GALLERY_REQUEST = 100;

    RecyclerView mRvSteps;
    RecyclerView mRvIngredients;
    StepsAdapter mStepsAdapter;
    IngredientsAdapter mIngredientsAdapter;
    List<String> mIngredientsList;
    List<String> mStepsList;

    private EditText mEtRecipeName;
    private EditText mEtStep;
    private EditText mEtIngredient;
    private ImageView mIvImage;
    private Button mBtnAddImage;
    private Button mBtnPost;

    private View view;
    private ParseFile file;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        mEtRecipeName = view.findViewById(R.id.etRecipeName);
        mEtStep = view.findViewById(R.id.etStep);
        mEtIngredient = view.findViewById(R.id.etIngredient);
        mIvImage = view.findViewById(R.id.ivImage);
        mBtnPost = view.findViewById(R.id.btnPost);
        mBtnAddImage = view.findViewById(R.id.btnAdd);

        file = ParseUser.getCurrentUser().getParseFile("profilePicture");

        mIvImage.setVisibility(View.GONE);

        mBtnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        mStepsList = new ArrayList<>();
        mIngredientsList = new ArrayList<>();

        StepsAdapter.OnLongClickListener onLongClickListener = new StepsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // deletes item from model
                mStepsList.remove(position);
                mStepsAdapter.notifyItemRemoved(position);
                mStepsAdapter.notifyDataSetChanged();
                mRvSteps.scrollToPosition(mStepsList.size() - 1);
            }
        };

        IngredientsAdapter.OnLongClickListener onLongClickListener1 = new StepsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                mIngredientsList.remove(position);
                mIngredientsAdapter.notifyItemRemoved(position);
                mRvIngredients.scrollToPosition(mIngredientsList.size() - 1);
            }
        };

        mRvIngredients = view.findViewById(R.id.rvIngredients);
        mIngredientsAdapter = new IngredientsAdapter(mIngredientsList, onLongClickListener1);
        mRvIngredients.setAdapter(mIngredientsAdapter);
        mRvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvIngredients.addItemDecoration(new DividerItemDecoration(mRvIngredients.getContext(), DividerItemDecoration.VERTICAL));


        mRvSteps = view.findViewById(R.id.rvSteps);
        mStepsAdapter = new StepsAdapter(mStepsList, onLongClickListener);
        mRvSteps.setAdapter(mStepsAdapter);
        mRvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvSteps.addItemDecoration(new DividerItemDecoration(mRvSteps.getContext(), DividerItemDecoration.VERTICAL));


        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = mEtRecipeName.getText().toString();
                if (recipeName.isEmpty()) {
                    Toast.makeText(getContext(), getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(recipeName, currentUser, mStepsList, mIngredientsList);

            }
        });

        mEtStep.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String step = mEtStep.getText().toString();
                if (!step.isEmpty()) {
                    // add step to model
                    mStepsList.add(step);
                    // notify adapter that item is inserted
                    mStepsAdapter.notifyItemInserted(mStepsList.size());
                    // clear edit text box
                    mEtStep.setText("");
                    mRvSteps.scrollToPosition(mStepsList.size() - 1);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        mEtIngredient.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String ingredient = mEtIngredient.getText().toString();
                if (!ingredient.isEmpty()) {
                    // add step to model
                    mIngredientsList.add(ingredient);
                    // notify adapter that item is inserted
                    mIngredientsAdapter.notifyItemInserted(mIngredientsList.size());
                    // clear edit text box
                    mEtIngredient.setText("");
                    mRvIngredients.scrollToPosition(mIngredientsList.size() - 1);
                }
                return true;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        Glide.with(view.getContext())
                                .load(bitmap)
                                .into(mIvImage);
                        mIvImage.setVisibility(View.VISIBLE);

                        file = conversionBitmapParseFile(bitmap);

                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        }
    }

    public ParseFile conversionBitmapParseFile(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return new ParseFile("image_file.png", imageByte);
    }


    private void savePost(String recipeName, ParseUser currentUser,
                          List<String> mStepsList, List<String> mIngredientsList) {
        Post post = new Post();
        post.setDescription(recipeName);
        post.setSteps(mStepsList);
        post.setIngredients(mIngredientsList);
        post.setImage(file);

        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                mEtRecipeName.setText("");
                mStepsList.clear();
                mIngredientsList.clear();
                mIvImage.setVisibility(View.GONE);
                ((MainActivity) getActivity()).postTransition();
            }
        });
    }
}