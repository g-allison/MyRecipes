package com.codepath.myrecipes.ui.profile.add;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.RecipeItem;
import com.codepath.myrecipes.models.Recipes;
import com.codepath.myrecipes.models.WeeklyMenu;
import com.codepath.myrecipes.ui.openingScreen.MainActivity;
import com.codepath.myrecipes.ui.profile.ProfileFragment;
import com.codepath.myrecipes.ui.profile.WeeklyMenuFragment;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.List;

public class AddRecyclerViewAdapter extends RecyclerView.Adapter<AddRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<RecipeItem> mData;
    private WeeklyMenu mDayOfWeek;

    public static final String TAG = "AddRecyclerViewAdapter";

    private WeeklyMenu mWeek;

    public AddRecyclerViewAdapter(Context mContext, List<RecipeItem> mData, WeeklyMenu mDayOfWeek) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDayOfWeek = mDayOfWeek;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_item_recipe,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTvRecipeTitle.setText(mData.get(position).getTitle());
        holder.mTvAmountOfDishes.setText(Integer.toString(mData.get(position).getAmountOfDishes()) );
        holder.mTvReadyIn.setText( Integer.toString(mData.get(position).getReadyInMins()) );
        if (mData.get(position).getThumbnail().isEmpty()) {
            holder.mIvRecipeImage.setImageResource(R.drawable.nopicture);
        } else{
            Glide.with(mContext)
                    .load(mData.get(position).getThumbnail())
                    .centerCrop()
                    .into(holder.mIvRecipeImage);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, "saving...", Toast.LENGTH_SHORT).show();

                String URL = mData.get(position).getThumbnail();
                Log.d(TAG, "onClick: " + URL);

                Recipes recipes = new Recipes();
                recipes.setRecipeName(mData.get(position).getTitle());
                recipes.setImageUrl(mData.get(position).getThumbnail());
                recipes.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(v.getContext(), v.getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "recipe save was successful!");
                    }
                });

                Log.d(TAG, "onClick: mDayOfWeek recipeName = " + mData.get(position).getTitle());

                mDayOfWeek.setRecipe(recipes);
                mDayOfWeek.setRecipeName(mData.get(position).getTitle());
                Log.d(TAG, "onClick: mDayOfWeek recipeName = " + mDayOfWeek.getRecipeName());
                mDayOfWeek.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(v.getContext(), v.getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "weekday save was successful!");
                    }
                });

//                AppCompatActivity activity = mContext.getApplicationContext();
                Fragment myFragment = new WeeklyMenuFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();


//                ((MainActivity)mContext.personalProfileTransition();
//                holder.getAc


//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                        Request.Method.GET,
//                        URL,
//                        null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
////                                    String[] temp = {(String) response.get("instructions")};
////                                    post.setSteps(temp);
//                                    Log.d(TAG, "onResponse: instructions = " + response.get("instructions"));
////                                    post.setIngredients(response.get("extendedIngredients"));
//
//
//                                    String url = response.get("image").toString();
//
//                                    mDayOfWeek.setRecipeName(mData.get(position).getTitle());
//                                    mDayOfWeek.setImage(mData.get(position).getThumbnail());
//
//
//                                } catch (JSONException e) {
//                                    Log.e(TAG, "onResponse: error", e);
//                                    e.printStackTrace();
//                                }
//                                Log.d(TAG, "onClick: instructions = " + post.getSteps());
////                                post.setUser(ParseUser.getCurrentUser());
//                                mDayOfWeek.saveInBackground(new SaveCallback() {
//                                    @Override
//                                    public void done(ParseException e) {
//                                        if (e != null) {
//                                            Log.e(TAG, "Error while saving", e);
//                                            Toast.makeText(v.getContext(), v.getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
//                                        }
//                                        Log.i(TAG, "Post save was successful!");
//
//                                    }
//                                });
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.i("the res is error:", error.toString());
//                            }
//                        }
//                );

//                requestQueue.add(jsonObjectRequest);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvRecipeTitle;
        TextView mTvAmountOfDishes;
        TextView mTvReadyIn;
        ImageView mIvRecipeImage;
        CardView cardView;

//        OnAddListener mOnAddListener;


        public MyViewHolder(View itemView) {
            super(itemView);
            mTvRecipeTitle = itemView.findViewById(R.id.recipe_title_id) ;
            mIvRecipeImage = itemView.findViewById(R.id.recipe_img_id);
            mTvAmountOfDishes = itemView.findViewById(R.id.servingTvLeft);
            mTvReadyIn = itemView.findViewById(R.id.readyInTvRight);
            cardView = itemView.findViewById(R.id.cardview_id);
        }
    }

    public interface OnAddListener{
        void onAddClick(DayOfWeek dayOfWeek);
    }
}
