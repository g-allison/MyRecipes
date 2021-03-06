package com.codepath.myrecipes.ui.profile;

import android.app.Activity;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.RecipeItem;
import com.codepath.myrecipes.models.Recipes;
import com.codepath.myrecipes.models.WeeklyMenu;
import com.codepath.myrecipes.ui.openingScreen.MainActivity;
import com.codepath.myrecipes.ui.postActivity.PostActivity;
import com.codepath.myrecipes.ui.profile.add.AddRecipeActivity;
import com.google.android.material.tabs.TabLayout;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.parceler.Parcels;

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
        private ImageView mIvDeleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvWeekday = itemView.findViewById(R.id.tvWeekday);
            mIvAddIcon = itemView.findViewById(R.id.ivAddIcon);
            mIvImage = itemView.findViewById(R.id.ivImage);
            mIvDeleteIcon = itemView.findViewById(R.id.ivDelete);
        }

        public void bind(WeeklyMenu dayOfWeek) {
            mTvWeekday.setText(dayOfWeek.getDay());

            Log.d(TAG, "bind: dayOfWeek " + dayOfWeek.getDay());
            Log.d(TAG, "bind: dayOfWeekRecipe " + dayOfWeek.getRecipe());

            if (!dayOfWeek.getRecipeName().isEmpty()) {
                Log.d(TAG, "bind: recipe name is " + dayOfWeek.getRecipeName());
                Log.d(TAG, "bind: recipe is present");

                ParseQuery<WeeklyMenu> query = ParseQuery.getQuery(WeeklyMenu.class);
                query.include(WeeklyMenu.KEY_RECIPE);
                query.getInBackground(dayOfWeek.getObjectId(), (object, e) -> {
                    if (e != null) {
                        Log.e(TAG, "bind: issue with query", e);
                    } else {
                        Recipes recipes = object.getRecipe();
                        try {
                            recipes.fetchIfNeeded();
                            Log.d(TAG, "recipe is fetched");
                            Log.d(TAG, "bind: " + recipes);
                            mIvImage.setVisibility(View.VISIBLE);
                            mIvDeleteIcon.setVisibility(View.VISIBLE);
                            mIvAddIcon.setVisibility(View.GONE);
                            Glide.with(mContext)
                                    .load(recipes.getImageUrl())
                                    .into(mIvImage);
                            mIvImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                Intent intent = new Intent(mContext, PostActivity.class);
//                                intent.putExtra("post", Parcels.wrap(post));
//                                mContext.startActivity(intent);
                                }
                            });
                        } catch (ParseException parseException) {
                            Log.e(TAG, "error fetching recipe" + parseException.getLocalizedMessage());
                        }

                    }

                });
            } else {
                Log.d(TAG, "bind: recipe is not there :(");
                mIvAddIcon.setVisibility(View.VISIBLE);
                mIvImage.setVisibility(View.GONE);
                mIvDeleteIcon.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(R.mipmap.instagram_new_post_outline_24)
                        .into(mIvAddIcon);
            }
            mIvAddIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddRecipeActivity.class);
                    intent.putExtra("recipe card", dayOfWeek);
                    ((Activity) mContext).startActivityForResult(intent, MainActivity.ADD_RECIPE_ACTIVITY_REQUEST_CODE);
                }
            });

            mIvDeleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "deleting...", Toast.LENGTH_SHORT).show();
//                    dayOfWeek.getRecipe().deleteInBackground(new DeleteCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                Toast.makeText(mContext, "Delete Successful", Toast.LENGTH_SHORT).show();
//                            } else {
//                                //Something went wrong while deleting the Object
//                                Toast.makeText(mContext, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });



                    dayOfWeek.setRecipeName("");
                    dayOfWeek.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(mContext, "Delete Successful", Toast.LENGTH_SHORT).show();
                            AppCompatActivity activity = (AppCompatActivity) mContext;
                            FragmentManager fm = activity.getSupportFragmentManager();


                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.simpleFrameLayout, new WeeklyMenuFragment());
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.commit();
                        }
                    });
                }
            });
        }
    }
}
