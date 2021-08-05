package com.codepath.myrecipes.ui.profile.add;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import com.codepath.myrecipes.models.RecipeItem;
import com.codepath.myrecipes.models.Recipes;
import com.codepath.myrecipes.models.WeeklyMenu;
import com.codepath.myrecipes.ui.openingScreen.MainActivity;
import com.codepath.myrecipes.ui.profile.ProfileFragment;
import com.codepath.myrecipes.ui.profile.WeeklyMenuFragment;
import com.parse.ParseException;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements AddRecyclerViewAdapter.OnAddListener {

    public static final String TAG = "AddRecipeActivity";
    public static final int COL_NUM = 2;

    private List<RecipeItem> mRecipeItem = new ArrayList<>();
    private List<RecipeItem> mSearchRecipeItem;
    private JSONArray mTestArr;
    private ImageButton mBtnSearch;
    private TextView mTvSearch;
    private TextView mTvEmpty;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private AddRecyclerViewAdapter mAddRecyclerViewAdapter;

    private WeeklyMenu mDayOfWeek;

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        mDayOfWeek = getIntent().getParcelableExtra("recipe card");
        bundle = savedInstanceState;

        mTvEmpty = findViewById(R.id.empty_view2);
        mProgressBar = findViewById(R.id.progressbar2);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), COL_NUM));

        mAddRecyclerViewAdapter = new AddRecyclerViewAdapter(getApplicationContext(), mRecipeItem, mDayOfWeek, this);

        getRandomRecipes();

        mTvSearch = findViewById(R.id.home_search_et);
        mBtnSearch = findViewById(R.id.home_search_btn);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                } catch (Exception e) {
                    Log.e(TAG, "onClick", e);
                }
                if(!mTvSearch.getText().toString().equals("")) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mRecyclerView.setAlpha(0);
                    searchRecipe(mTvSearch.getText().toString());
                }
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.type_something), Toast.LENGTH_LONG).show();
            }
        });
        mTvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!v.getText().toString().equals("")) {
                        mTvEmpty.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mRecyclerView.setAlpha(0);
                        searchRecipe(v.getText().toString());
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.type_something), Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

    }

    private void searchRecipe(String search) {
        mSearchRecipeItem = new ArrayList<>();
        mAddRecyclerViewAdapter = new AddRecyclerViewAdapter(getApplicationContext(), mSearchRecipeItem, mDayOfWeek, this);
        String URL = "https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=f839acb471114d05a8094ee6d32f7e57";
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
                                mSearchRecipeItem.add(new RecipeItem(jsonObject1.optString("id"), jsonObject1.optString("title"), "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            mProgressBar.setVisibility(View.GONE);
                            if (mSearchRecipeItem.isEmpty()) {
                                mRecyclerView.setAlpha(0);
                                mTvEmpty.setVisibility(View.VISIBLE);
                            }
                            else {
                                mTvEmpty.setVisibility(View.GONE);
                                mRecyclerView.setAdapter(mAddRecyclerViewAdapter);
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
                                mRecipeItem.add(new RecipeItem(jsonObject1.optString("id"), jsonObject1.optString("title"), jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            mProgressBar.setVisibility(View.GONE);
                            mRecyclerView.setAdapter(mAddRecyclerViewAdapter);
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
                        mTvEmpty.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onAddClick(RecipeItem recipeItem) {
        Toast.makeText(getApplicationContext(), "saving...", Toast.LENGTH_SHORT).show();

        String thumbnailURL = recipeItem.getThumbnail();
        Log.d(TAG, "onClick: " + thumbnailURL);

        Recipes recipes = new Recipes();
        recipes.setRecipeName(recipeItem.getTitle());
        recipes.setImageUrl(recipeItem.getThumbnail());

        // #####
//        ArrayList<String> temp = new ArrayList<>();
//        temp.add("Milk");
//        recipes.setIngredients(temp);

        String URL = " https://api.spoonacular.com/recipes/" + recipeItem.getId() + "/information?apiKey=c957b6816ba048139fbc25a67d2cff33";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ArrayList<String> ingredientsLst = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "onResponse: success");
                            JSONArray ingredientsArr = (JSONArray) response.get("extendedIngredients");
                            Log.d(TAG, "onResponse: ingredientsArr " + ingredientsArr);
                            for (int i = 0; i < ingredientsArr.length(); i++) {
                                JSONObject jsonObject1 = ingredientsArr.getJSONObject(i);
                                String ingredient = jsonObject1.getString("name");
                                Log.d(TAG, "onResponse: ingredient " + ingredient);
                                ingredientsLst.add(ingredient);
                            }
                            Log.d(TAG, "onAddClick: ingredientsLst " + ingredientsLst);
                            recipes.setIngredients(ingredientsLst);
                            Log.d(TAG, "onResponse: recipes " + recipes.getIngredients());
                            recipes.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Log.e(TAG, "Error while saving", e);
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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




        mDayOfWeek.setRecipe(recipes);
        mDayOfWeek.setRecipeName(recipeItem.getTitle());
        Log.d(TAG, "onClick: mDayOfWeek recipeName = " + mDayOfWeek.getRecipeName());
        mDayOfWeek.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "weekday save was successful!");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }
}