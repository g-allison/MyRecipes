package com.codepath.myrecipes.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.MainActivity;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.WeeklyMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    public static final String TAG = "ProfileAdapter";
    private Context mContext;
    private List<WeeklyMenu> mDaysOfWeek;

    public ProfileAdapter(Context mContext, List<WeeklyMenu> mDaysOfWeek) {
        this.mContext = mContext;
        this.mDaysOfWeek = mDaysOfWeek;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weekday, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeeklyMenu dayOfWeek = mDaysOfWeek.get(position);
        holder.bind(dayOfWeek);

    }

    @Override
    public int getItemCount() {
        return mDaysOfWeek.size();
    }

    public void clear() {
        mDaysOfWeek.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvWeekday;
        private ImageView mIvAddIcon;
        private ImageView mIvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvWeekday = itemView.findViewById(R.id.tvWeekday);
            mIvAddIcon = itemView.findViewById(R.id.ivAddIcon);
            mIvImage = itemView.findViewById(R.id.ivImage);
        }

        public void bind(WeeklyMenu dayOfWeek) {
            mTvWeekday.setText(dayOfWeek.getDay());

            if (dayOfWeek.getRecipe() != null) {
                ParseQuery<WeeklyMenu> query = ParseQuery.getQuery(WeeklyMenu.class);
                query.include(WeeklyMenu.KEY_RECIPE);
                query.getInBackground(dayOfWeek.getObjectId(), (object, e) -> {
                    if (e != null) {
                        Log.e(TAG, "bind: issue with query", e);
                    } else {
                        Log.d(TAG, "bind: " + object.getRecipe());
                        Post post = object.getRecipe();
                        mIvImage.setVisibility(View.VISIBLE);
                        mIvAddIcon.setVisibility(View.GONE);
                        Glide.with(mContext)
                                .load(post.getImage().getUrl())
                                .into(mIvImage);
                    }

                });


            } else {
                Log.d(TAG, "bind: null");
                mIvAddIcon.setVisibility(View.VISIBLE);
                mIvImage.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(R.mipmap.instagram_new_post_outline_24)
                        .into(mIvAddIcon);
            }
            mIvAddIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "new recipes in the works", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, AddRecipeActivity.class);
                    intent.putExtra("recipe card", dayOfWeek);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
