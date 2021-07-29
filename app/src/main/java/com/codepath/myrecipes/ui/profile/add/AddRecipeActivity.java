package com.codepath.myrecipes.ui.profile.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.Recipe;
import com.codepath.myrecipes.models.WeeklyMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddRecipeActivity";
    public static final int COL_NUM = 2;

    private TextView mDayName;

    private List<Recipe> mRecipe = new ArrayList<>();
    private List<Recipe> mSearchRecipe;
    private JSONArray mTestArr;
    private ImageButton mSearchBtn;
    private TextView mSearchTv;
    private TextView mEmptyView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

//        mDayName = findViewById(R.id.tvDayName);
//        WeeklyMenu day = getIntent().getParcelableExtra("recipe card");
//        mDayName.setText(day.getDay());

        mEmptyView = findViewById(R.id.empty_view2);
        mProgressBar = findViewById(R.id.progressbar2);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), COL_NUM));

        getRandomRecipes();

        mSearchTv = findViewById(R.id.home_search_et);
        mSearchBtn = findViewById(R.id.home_search_btn);
        mSearchBtn.setOnClickListener(this);
        mSearchTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(!v.getText().toString().equals("")) {
                        mEmptyView.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mRecyclerView.setAlpha(0);
                        searchRecipe(v.getText().toString());
                    }
                    else
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.type_something), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

    }

    private void searchRecipe(String search) {
        mSearchRecipe = new ArrayList<Recipe>();
        final String URL = "https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=f839acb471114d05a8094ee6d32f7e57";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTestArr = (JSONArray) response.get("results");
                            Log.i("the search res is:", String.valueOf(mTestArr));
                            for (int i = 0; i < mTestArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = mTestArr.getJSONObject(i);
                                mSearchRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            mProgressBar.setVisibility(View.GONE);
                            if (mSearchRecipe.isEmpty()) {
                                mRecyclerView.setAlpha(0);
                                mEmptyView.setVisibility(View.VISIBLE);
                            }
                            else {
                                mEmptyView.setVisibility(View.GONE);
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getApplicationContext(), mSearchRecipe);
                                mRecyclerView.setAdapter(myAdapter);
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mRecyclerView.setAlpha(1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    }

    private void getRandomRecipes() {
        final String URL = " https://api.spoonacular.com/recipes/random?number=30&instructionsRequired=true&" +
                "apiKey=f839acb471114d05a8094ee6d32f7e57";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTestArr = (JSONArray) response.get("recipes");
                            Log.i("the res is:", String.valueOf(mTestArr));
                            for (int i = 0; i < mTestArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = mTestArr.getJSONObject(i);
                                mRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            mProgressBar.setVisibility(View.GONE);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getApplicationContext(), mRecipe);
                            mRecyclerView.setAdapter(myAdapter);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setAlpha(0);
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        try {
            InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Exception e) {
        }
        if(!mSearchTv.getText().toString().toString().equals("")) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setAlpha(0);
            searchRecipe(mSearchTv.getText().toString());
        }
        else
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.type_something), Toast.LENGTH_LONG).show();
    }

}