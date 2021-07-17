package com.codepath.myrecipes.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.codepath.myrecipes.BitmapScaler;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context mContext;
    private List<Post> mPosts;
    private OnPostListener mOnPostListener;

    public PostsAdapter(Context mContext, List<Post> mPosts, OnPostListener onPostListener) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.mOnPostListener = onPostListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view, mOnPostListener);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvUsername;
        private TextView mTvDescription;
        private RelativeLayout mRlContainer;
        private ImageView mIvImage;

        OnPostListener onPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mIvImage = itemView.findViewById(R.id.ivImage);
            mRlContainer = itemView.findViewById(R.id.rlContainer);
            this.onPostListener = onPostListener;
        }

        public void bind(Post post) {
            mRlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPostListener.onPostClick(getAdapterPosition());
                }
            });

            // bind data to the view
            mTvDescription.setText(post.getDescription());
            mTvUsername.setText("@" + post.getUser().getUsername());

            if (post.getImage() != null) {
                mIvImage.setVisibility(View.VISIBLE);

                Glide.with(mContext)
                        .load(post.getImage().getUrl())
                        .into(new DrawableImageViewTarget(mIvImage, /*waitForLayout=*/ true));
            } else {
                mIvImage.setVisibility(View.GONE);
            }
        }
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }
}
