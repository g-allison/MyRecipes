package com.codepath.myrecipes.ui.profile.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.RecipeItem;
import com.codepath.myrecipes.models.WeeklyMenu;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddRecyclerViewAdapter extends RecyclerView.Adapter<AddRecyclerViewAdapter.ViewHolder> {

    public Context mContext;
    private List<RecipeItem> mData;
    private WeeklyMenu mDayOfWeek;

    OnAddListener mOnAddListener;

    public static final String TAG = "AddRecyclerViewAdapter";

    private WeeklyMenu mWeek;

    public AddRecyclerViewAdapter(Context mContext, List<RecipeItem> mData, WeeklyMenu mDayOfWeek, OnAddListener mAddListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDayOfWeek = mDayOfWeek;
        this.mOnAddListener = mAddListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_item_recipe,parent,false);
        return new ViewHolder(view, mOnAddListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeItem recipeItem = mData.get(position);
        holder.bind(recipeItem);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvRecipeTitle;
        TextView mTvAmountOfDishes;
        TextView mTvReadyIn;
        ImageView mIvRecipeImage;
        CardView cardView;

        OnAddListener mOnAddListener;

        public ViewHolder(View itemView, OnAddListener onAddListener) {
            super(itemView);
            mTvRecipeTitle = itemView.findViewById(R.id.recipe_title_id);
            mIvRecipeImage = itemView.findViewById(R.id.recipe_img_id);
            mTvAmountOfDishes = itemView.findViewById(R.id.servingTvLeft);
            mTvReadyIn = itemView.findViewById(R.id.readyInTvRight);
            cardView = itemView.findViewById(R.id.cardview_id);
            this.mOnAddListener = onAddListener;
        }

        public void bind(RecipeItem recipeItem) {
            mTvRecipeTitle.setText(recipeItem.getTitle());
            mTvAmountOfDishes.setText((String.valueOf(recipeItem.getAmountOfDishes())));
            mTvReadyIn.setText(String.valueOf(recipeItem.getReadyInMins()));
            if (recipeItem.getThumbnail().isEmpty()) {
                mIvRecipeImage.setImageResource(R.drawable.nopicture);
            } else {
                Glide.with(mContext)
                        .load(recipeItem.getThumbnail())
                        .centerCrop()
                        .into(mIvRecipeImage);
            }

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddListener.onAddClick(recipeItem);
                }
            });
        }
    }

    public interface OnAddListener {
        void onAddClick(RecipeItem recipeItem);
    }
}
