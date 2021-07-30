package com.codepath.myrecipes.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.parse.ParseFile;

import java.util.List;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> mPosts;
    private OnPostListener mOnPostListener;
    private LayoutInflater mInflater;

    public GridAdapter(Context mContext, List<Post> mPosts, OnPostListener mOnPostListener) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.mOnPostListener = mOnPostListener;
        this.mInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, int position) {
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mIvGrid;
        private OnPostListener mOnPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener mOnPostListener) {
            super(itemView);
            mIvGrid = itemView.findViewById(R.id.ivGrid);

            itemView.setOnClickListener(this);
            this.mOnPostListener = mOnPostListener;
        }

        public void bind(Post post) {
            // Binding the post data to the view elements
            ParseFile image = post.getImage();
            Glide.with(mContext)
                    .load(image.getUrl())
                    .centerCrop()
                    .into(mIvGrid);
        }

        @Override
        public void onClick(View v) {
            mOnPostListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }
}
