package com.codepath.myrecipes.ui.post;

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

import com.codepath.myrecipes.MainActivity;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class DashboardFragment extends Fragment {
    public static final String TAG = "DashboardFragment";

//    private EditText mEtSteps;
    private EditText mEtRecipeName;
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

//        mEtSteps = view.findViewById(R.id.etSteps);
        mEtRecipeName = view.findViewById(R.id.etRecipeName);
        mBtnPost = view.findViewById(R.id.btnPost);

        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = mEtRecipeName.getText().toString();
                if (recipeName.isEmpty()) {
                    Toast.makeText(getContext(), "Title empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(recipeName, currentUser);

            }
        });
    }

    private void savePost(String recipeName, ParseUser currentUser) {
        Post post = new Post();
        post.setDescription(recipeName);

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
                ((MainActivity)getActivity()).postTransition();
            }
        });
    }
}