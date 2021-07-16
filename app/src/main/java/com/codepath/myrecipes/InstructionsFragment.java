package com.codepath.myrecipes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionsFragment extends Fragment {
    private static final String TAG = "InstructionsFragment";
    private TextView mTvSteps;

    public InstructionsFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instructions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Post post = (Post) Parcels.unwrap(getActivity().getIntent().getParcelableExtra("post"));
        Log.d(TAG, "onViewCreated: post " + post);

        mTvSteps = view.findViewById(R.id.tvSteps);

        ArrayList<String> instructionsList = (ArrayList<String>) post.getKeySteps();
        StringBuilder sb = new StringBuilder();
        if (post.getKeySteps() != null) {
            for (int i = 0; i < instructionsList.size(); i++) {
                String step = instructionsList.get(i);
                if (i != 0) {
                    sb.append("\n");
                }
                sb.append(i+1);
                sb.append(". ");
                sb.append(step);
                sb.append("\n");
            }
            mTvSteps.setText(sb);
        }
    }
}