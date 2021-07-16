package com.codepath.myrecipes.ui.compose;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.myrecipes.MainActivity;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    public static final String TAG = "DashboardFragment";

    RecyclerView mRvSteps;
    RecyclerView mRvIngredients;
    StepsAdapter mStepsAdapter;
    IngredientsAdapter mIngredientsAdapter;
    List<String> mIngredientsList;
    List<String> mStepsList;

    private EditText mEtRecipeName;
    private EditText mEtStep;
    private EditText mEtIngredient;
    private Button mBtnPost;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEtRecipeName = view.findViewById(R.id.etRecipeName);
        mEtStep = view.findViewById(R.id.etStep);
        mBtnPost = view.findViewById(R.id.btnPost);
        mEtIngredient = view.findViewById(R.id.etIngredient);
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
                    Toast.makeText(getContext(), "Title empty", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "field is empty", Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(getContext(), "field is empty", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

//        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
//            @Override
//            public void onSwipeRight() {
//                String step = mEtStep.getText().toString();
//                if (!step.isEmpty()) {
//                    // add step to model
//                    mSteps.add(step);
//                    // notify adapter that item is inserted
//                    mItemsAdapter.notifyItemInserted(mSteps.size());
//                    // clear edit text box
//                    mEtStep.setText("");
//                    mRvSteps.scrollToPosition(mSteps.size() - 1);
//                } else {
//                    Toast.makeText(getContext(), "field is empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void savePost(String recipeName, ParseUser currentUser,
                          List<String> mStepsList, List<String> mIngredientsList) {
        Post post = new Post();
        post.setDescription(recipeName);
        post.setKeySteps(mStepsList);
        post.setKeyIngredients(mIngredientsList);

        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                mEtRecipeName.setText("");
                mStepsList.clear();
                mIngredientsList.clear();
                ((MainActivity) getActivity()).postTransition();
            }
        });
    }
}