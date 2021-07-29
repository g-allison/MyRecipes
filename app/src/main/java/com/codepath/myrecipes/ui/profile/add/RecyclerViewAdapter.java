package com.codepath.myrecipes.ui.profile.add;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.Recipe;
import com.codepath.myrecipes.models.WeeklyMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> mData;

    public static final String TAG = "RecyclerViewAdapter";

    private WeeklyMenu mWeek;

    public RecyclerViewAdapter(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_recipe,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_recipe_title.setText(mData.get(position).getTitle());
        holder.tv_amount_of_dishes.setText(Integer.toString(mData.get(position).getAmountOfDishes()) );
        holder.tv_ready_in_mins.setText( Integer.toString(mData.get(position).getReadyInMins()) );
        if (mData.get(position).getThumbnail().isEmpty()) {
            holder.img_recipe_thumbnail.setImageResource(R.drawable.nopicture);
        } else{
            Glide.with(mContext)
                    .load(mData.get(position).getThumbnail())
                    .centerCrop()
                    .into(holder.img_recipe_thumbnail);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL = " https://api.spoonacular.com/recipes/" + mData.get(position).getId() + "/information?apiKey=c957b6816ba048139fbc25a67d2cff33";

                Post post = new Post();
                post.setDescription(mData.get(position).getTitle());
                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
//                                    String[] temp = {(String) response.get("instructions")};
//                                    post.setSteps(temp);
                                    Log.d(TAG, "onResponse: instructions = " + response.get("instructions"));
//                                    post.setIngredients(response.get("extendedIngredients"));

//                                    URL url = new URL(response.get("image").toString());

//                                    ParseFile file = new ParseFile(url, "image.png");
                                    String url = response.get("image").toString();




//                                    post.setImage((ParseFile) response.get("image"));
//                                    Intent intent = new Intent(this, WeeklyMenuFragment.class);

                                } catch (JSONException e) {
                                    Log.e(TAG, "onResponse: error", e);
                                    e.printStackTrace();
                                }
                                Log.d(TAG, "onClick: instructions = " + post.getSteps());
//                                post.setUser(ParseUser.getCurrentUser());
//                                post.saveInBackground(new SaveCallback() {
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
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("the res is error:", error.toString());
                            }
                        }
                );

                requestQueue.add(jsonObjectRequest);
                Toast.makeText(mContext, "added: " + post.getDescription(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_recipe_title,tv_amount_of_dishes,tv_ready_in_mins;
        ImageView img_recipe_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_recipe_title = (TextView) itemView.findViewById(R.id.recipe_title_id) ;
            img_recipe_thumbnail = (ImageView) itemView.findViewById(R.id.recipe_img_id);
            tv_amount_of_dishes = (TextView) itemView.findViewById(R.id.servingTvLeft);
            tv_ready_in_mins = (TextView) itemView.findViewById(R.id.readyInTvRight);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
