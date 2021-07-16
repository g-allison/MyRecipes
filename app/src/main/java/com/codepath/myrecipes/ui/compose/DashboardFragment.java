package com.codepath.myrecipes.ui.compose;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.myrecipes.MainActivity;
import com.codepath.myrecipes.OnSwipeTouchListener;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    public static final String TAG = "DashboardFragment";

    RecyclerView mRvItems;
    StepAdapter mItemsAdapter;
    List<String> mItems;

    private EditText mEtRecipeName;
    private EditText mEtStep;
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

//        relativeLayout = view.findViewById(R.id.relative_layout);
//        swipeListener = new SwipeListener(relativeLayout);

        mEtRecipeName = view.findViewById(R.id.etRecipeName);
        mEtStep = view.findViewById(R.id.etStep);
        mBtnPost = view.findViewById(R.id.btnPost);
        mRvItems = view.findViewById(R.id.rvItems);

        mItems = new ArrayList<>();

        StepAdapter.OnLongClickListener onLongClickListener = new StepAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // deletes item from model
                mItems.remove(position);
                mItemsAdapter.notifyItemRemoved(position);
                mItemsAdapter.notifyDataSetChanged();
                mRvItems.scrollToPosition(mItems.size() - 1);
            }
        };

        mItemsAdapter = new StepAdapter(mItems, onLongClickListener);
        mRvItems.setAdapter(mItemsAdapter);
        mRvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvItems.addItemDecoration(new DividerItemDecoration(mRvItems.getContext(), DividerItemDecoration.VERTICAL));


        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = mEtRecipeName.getText().toString();
                if (recipeName.isEmpty()) {
                    Toast.makeText(getContext(), "Title empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(recipeName, currentUser, mItems);

            }
        });

        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeRight() {
//                Toast.makeText(getActivity(), "RIGHT", Toast.LENGTH_SHORT).show();
                String step = mEtStep.getText().toString();
                if (!step.isEmpty()) {
                    // add step to model
                    mItems.add(step);
                    // notify adapter that item is inserted
                    mItemsAdapter.notifyItemInserted(mItems.size());
                    // clear edit text box
                    mEtStep.setText("");
                    mRvItems.scrollToPosition(mItems.size() - 1);
                } else {
                    Toast.makeText(getContext(), "field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void savePost(String recipeName, ParseUser currentUser, List<String> mItems) {
        Post post = new Post();
        post.setDescription(recipeName);
        post.setKeySteps(mItems);

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
                mItems.clear();
                ((MainActivity) getActivity()).postTransition();
            }
        });
    }
}