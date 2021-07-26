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
import com.codepath.myrecipes.ui.profile.add.Recipe;
import com.codepath.myrecipes.ui.WeeklyMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddRecipeActivity";

    private TextView mDayName;

    //
    private List<Recipe> lstRecipe = new ArrayList<>();
    private List<Recipe> searchRecipe;
    private JSONArray testArr;
    private ImageButton searchBtn;
//    private Button breakfastBtn,lunchBtn,dinnerBtn;
    private TextView searchTv, emptyView;
    private RecyclerView myrv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

//        mDayName = findViewById(R.id.tvDayName);

//        WeeklyMenu day = getIntent().getParcelableExtra("recipe card");
//        mDayName.setText(day.getDay());


        emptyView = findViewById(R.id.empty_view2);
        progressBar = findViewById(R.id.progressbar2);
        myrv = findViewById(R.id.recyclerview);
        myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        getRandomRecipes();
        searchTv = findViewById(R.id.home_search_et);
        searchBtn = findViewById(R.id.home_search_btn);
        searchBtn.setOnClickListener(this);
        searchTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(!v.getText().toString().equals("")) {
                        emptyView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        myrv.setAlpha(0);
                        searchRecipe(v.getText().toString());
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Type something...", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

    }

    private void searchRecipe(String search) {
        searchRecipe = new ArrayList<Recipe>();
        String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=f839acb471114d05a8094ee6d32f7e57";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("results");
                            Log.i("the search res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                searchRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            progressBar.setVisibility(View.GONE);
                            if(searchRecipe.isEmpty()){
                                myrv.setAlpha(0);
                                emptyView.setVisibility(View.VISIBLE);
                            }
                            else{
                                emptyView.setVisibility(View.GONE);
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getApplicationContext(), searchRecipe);
                                myrv.setAdapter(myAdapter);
                                myrv.setItemAnimator(new DefaultItemAnimator());
                                myrv.setAlpha(1);
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
        String URL = " https://api.spoonacular.com/recipes/random?number=30&instructionsRequired=true&apiKey=f839acb471114d05a8094ee6d32f7e57";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("recipes");
                            Log.i("the res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                lstRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            progressBar.setVisibility(View.GONE);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getApplicationContext(), lstRecipe);
                            myrv.setAdapter(myAdapter);
                            myrv.setItemAnimator(new DefaultItemAnimator());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                        progressBar.setVisibility(View.GONE);
                        myrv.setAlpha(0);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick not sure what happens here");

        try {
            InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getApplicationContext().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
        if(!searchTv.getText().toString().toString().equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            myrv.setAlpha(0);
            searchRecipe(searchTv.getText().toString());
        }
        else
            Toast.makeText(getApplicationContext(), "Type something...", Toast.LENGTH_LONG).show();
    }

}