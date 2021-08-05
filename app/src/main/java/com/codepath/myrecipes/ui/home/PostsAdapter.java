package com.codepath.myrecipes.ui.home;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.codepath.myrecipes.models.Heart;
import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.postActivity.PostActivity;
import com.parse.ParseUser;

import org.parceler.Parcels;

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

        private ImageView mHeartWhite;
        private ImageView mHeartRed;
        private GestureDetector mGestureDetector;
        public Heart mHeart;

        OnPostListener mOnPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            mTvUsername = itemView.findViewById(R.id.tvUsername);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mIvImage = itemView.findViewById(R.id.ivImage);
            mRlContainer = itemView.findViewById(R.id.rlContainer);

            mHeartRed = itemView.findViewById(R.id.image_heart_red);
            mHeartWhite = itemView.findViewById(R.id.image_heart);
            mGestureDetector = new GestureDetector(itemView.getContext(), new GestureListener());

            this.mOnPostListener = onPostListener;

        }

        public void bind(Post post) {
            mTvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnPostListener.onPostClick(post.getUser());
                }
            });

            // bind data to the view
            mTvDescription.setText(post.getDescription());
            mTvUsername.setText(itemView.getResources().getString(R.string.ampersand) + post.getUser().getUsername());
//
            mIvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PostActivity.class);
                    intent.putExtra("post", Parcels.wrap(post));
                    mContext.startActivity(intent);
                }
            });

            if (post.getImage() != null) {
                mIvImage.setVisibility(View.VISIBLE);

                Glide.with(mContext)
                        .load(post.getImage().getUrl())
                        .into(new DrawableImageViewTarget(mIvImage, /*waitForLayout=*/ true));
            } else {
                mIvImage.setVisibility(View.GONE);
            }

            mHeartRed.setVisibility(View.GONE);
            mHeartWhite.setVisibility(View.VISIBLE);
            mHeart = new Heart(mHeartWhite, mHeartRed);
            testToggle();
        }

        private void testToggle() {
            mHeartRed.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("PostsAdapter", "onTouch: red heart touch detected");

                    return mGestureDetector.onTouchEvent(event);
                }
            });

            mHeartWhite.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("PostsAdapter", "onTouch: white heart touch detected");
                    return mGestureDetector.onTouchEvent(event);
                }
            });

        }

        public class GestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("TAG", "onDoubleTap: double tap detected");
                mHeart.toggleLike();
                return true;
            }
        }
    }

    public interface OnPostListener {
        void onPostClick(ParseUser user);
    }
}
