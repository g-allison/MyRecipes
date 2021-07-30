package com.codepath.myrecipes.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.ui.profile.add.AddRecyclerViewAdapter;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    public static final String TAG = "SearchRecyclerViewAdapter";

    private Context mContext;
    private List<ParseUser> mSearchUser;
    private OnSearchListener mOnSearchListener;

    public SearchRecyclerViewAdapter(Context mContext, List<ParseUser> mSearchUser, OnSearchListener onSearchListener) {
        this.mContext = mContext;
        this.mSearchUser = mSearchUser;
        this.mOnSearchListener = onSearchListener;

    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.item_search, parent,false);
        return new MyViewHolder(view, mOnSearchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        ParseUser user = mSearchUser.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return mSearchUser.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mSearchUser.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvProfilePicture;
        TextView mTvUsername;
        private RelativeLayout mRlContainer;

        OnSearchListener mOnSearchListener;

        public MyViewHolder(View itemView, OnSearchListener onSearchListener) {
            super(itemView);
            mIvProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mRlContainer = itemView.findViewById(R.id.rlContainer);
            mOnSearchListener = onSearchListener;
        }

        public void bind(ParseUser user) {
            mRlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnSearchListener.onSearchClick(user);
                }
            });

            mTvUsername.setText(user.getUsername());
            Glide.with(mContext)
                    .load(user.getParseFile("profilePicture").getUrl())
                    .circleCrop()
                    .into(mIvProfilePicture);
        }
    }

    public interface OnSearchListener {
        void onSearchClick(ParseUser user);
    }
}
