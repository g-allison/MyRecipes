package com.codepath.myrecipes.ui.profile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
            ParseFile dayImage = dayOfWeek.getImage();

//            String recipeName = ((Post) dayOfWeek.getRecipe()).getDescription();
//            Post post = (Post) dayOfWeek.getRecipe();

            if (dayImage != null) {
                Log.d(TAG, "day has image: " + dayImage.getUrl());
                mIvImage.setVisibility(View.VISIBLE);
                mIvAddIcon.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(dayOfWeek.getImage().getUrl())
                        .into(mIvImage);

            } else {
                mIvAddIcon.setVisibility(View.VISIBLE);
                mIvImage.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(R.mipmap.instagram_new_post_outline_24)
                        .into(mIvAddIcon);
            }

//            ArrayList<WeeklyMenu> results = new ArrayList<>();
//
//            ParseQuery<WeeklyMenu> query = ParseQuery.getQuery(WeeklyMenu.class);
//            query.include(WeeklyMenu.KEY_DAY);
//            query.setLimit(7);
//            query.findInBackground(new FindCallback<WeeklyMenu>() {
//                @Override
//                public void done(List<WeeklyMenu> days, ParseException e) {
//                    // checks for errors
//                    if (e != null) {
//                        Log.e(TAG, "Issue with WeeklyMenu query", e);
//                        return;
//                    }
//
//                    for (WeeklyMenu day : days) {
//                        Log.d(TAG, "their post ID: " + day);
//                        Log.d(TAG, "results: " + day);
//                    }
//                    results.addAll(days);
//
//                }
//            });


        }
    }












}
