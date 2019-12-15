package com.example.cuisinhelha.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.adapters.RecipeIngredientAdapter;
import com.example.cuisinhelha.adapters.RecipeReviewAdapter;
import com.example.cuisinhelha.adapters.RecipeStepAdapter;
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;
import com.example.cuisinhelha.models.Ingredient;
import com.example.cuisinhelha.models.Picture;
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.models.Review;
import com.example.cuisinhelha.models.Step;
import com.example.cuisinhelha.repositories.RecipeRepository;
import com.example.cuisinhelha.repositories.UserRepository;
import com.example.cuisinhelha.services.IngredientRepositoryService;
import com.example.cuisinhelha.services.PictureRepositoryService;
import com.example.cuisinhelha.services.RecipeRepositoryService;
import com.example.cuisinhelha.services.ReviewRepositoryService;
import com.example.cuisinhelha.services.StepRepositoryService;
import com.example.cuisinhelha.services.UserRepositoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetail extends AppCompatActivity implements IHeaderNavigation {

    private TextView tv_recipeTitle;
    private TextView tv_recipeType;
    private TextView tv_prepTime;
    private TextView tv_persons;
    private EditText et_review;
    private RatingBar rb_rating;
    private Button btn_addReview;
    private ImageView imgRecipe;
    private Recipe currentRecipe;
    private int recipeID;

    private ListView lvIngredients;
    private ListView lvSteps;
    private ListView lvReviews;

    private RecipeIngredientAdapter ingredientAdapter;
    private RecipeStepAdapter stepAdapter;
    private RecipeReviewAdapter reviewAdapter;

    private List<Ingredient>ingredients;
    private List<Step>steps;
    private List<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        reviews = new ArrayList<>();
        tv_recipeTitle = findViewById(R.id.recipeTitleTV);
        imgRecipe = findViewById(R.id.imgRecipe);

        //add review
        btn_addReview = findViewById(R.id.btnPostReview);
        rb_rating = findViewById(R.id.ratingBarReview);
        et_review = findViewById(R.id.etMessageReview);

        //listViews
        lvIngredients = findViewById(R.id.ingredientList);
        lvSteps = findViewById(R.id.lvSteps);
        lvReviews = findViewById(R.id.lvReview);

        tv_persons = findViewById(R.id.tvPersons);
        tv_prepTime = findViewById(R.id.tvPrepTime);
        tv_recipeType = findViewById(R.id.tvRecipeType);

        recipeID = getIntent().getIntExtra(RecipeSearchActivity.EXTRA_SEARCH_ACTIVITY, 1);

        //adapters
        stepAdapter = new RecipeStepAdapter(this, R.id.lvSteps, steps);
        ingredientAdapter = new RecipeIngredientAdapter(this, R.id.ingredientList, ingredients);
        reviewAdapter = new RecipeReviewAdapter(this, R.id.lvReview, reviews);


        lvIngredients.setAdapter(ingredientAdapter);
        lvSteps.setAdapter(stepAdapter);
        lvReviews.setAdapter(reviewAdapter);

        loadRecipeDetails();

    }

    public void loadRecipeDetails(){

        RecipeRepositoryService.queryByRecipe(recipeID).enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                //Log.wtf("detail", response.body().get(0).toString());
                currentRecipe = response.body().get(0);

                updateRecipe();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.wtf("ERROR", "Recipe not load");
            }
        });

    }

    //TODO Picture en commentaire, en attente d'image
    public void updateRecipe(){
        loadStep();
        loadIngredient();
        loadReview();
        //loadPicture();
        tv_recipeTitle.setText(currentRecipe.getNameRecipe());

        tv_recipeType.setText(currentRecipe.getRecipeType());
        tv_prepTime.setText(currentRecipe.getPrepTime() + "");
        tv_persons.setText(currentRecipe.getPersons()+"");

    }

    public void loadIngredient(){
        IngredientRepositoryService.queryByRecipe(recipeID).enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {

                ingredients.clear();
                ingredients.addAll(response.body());

                Log.i("INGREDIENT", ingredients.toString());
                ingredientAdapter.notifyDataSetChanged();
                updateListViewSize(lvIngredients);
            }
            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                Log.wtf("ERROR", "ingredients not lead");
            }
        });
    }
    public void loadStep(){

        StepRepositoryService.queryByRecipe(recipeID).enqueue(new Callback<List<Step>>() {
            @Override
            public void onResponse(Call<List<Step>> call, Response<List<Step>> response) {
                steps.clear();
                steps.addAll(response.body());
                stepAdapter.notifyDataSetChanged();
                updateListViewSize(lvSteps);
                Log.i("STEP", steps.toString());


            }

            @Override
            public void onFailure(Call<List<Step>> call, Throwable t) {
                Log.wtf("ERROR", "Steps not load");
            }
        });
    }
    public void loadReview(){
        ReviewRepositoryService.queryByRecipe(recipeID).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                reviews.clear();
                reviews.addAll(response.body());
                Log.i("REVIEW", reviews.toString());
                reviewAdapter.notifyDataSetChanged();
                updateListViewSize(lvReviews);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.wtf("ERROR", "Review not load");
            }
        });
    }
    public void postReview(View view) {
        Review review = new Review();

        SharedPreferences pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        int id = pref.getInt(UserPreferences.ID_USER, -1);

        review.setIdUser(id);
        review.setIdRecipe(currentRecipe.getIdRecipe());
        review.setReviewMessage(et_review.getText().toString());
        review.setRate((int) rb_rating.getRating());

        if (!reviews.contains(review)){

            ReviewRepositoryService.post(review).enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    //Log.i("POST REVIEW", response.body().toString());
                    loadReview();
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.wtf("ERROR", "Review not posted");
                }
            });
        }
        else {

            Toast toast = Toast.makeText(getApplicationContext(), "Already posted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //TODO PEUT ETRE A REFAIRE
    private void loadPicture(){
        PictureRepositoryService.queryByRecipe(recipeID).enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                Picture respPicture = (Picture) response.body();
                byte[] data = Base64.decode(respPicture.getPicture(), Base64.DEFAULT);
                Bitmap respImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                imgRecipe.setImageBitmap(respImage);
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {
                Log.wtf("ERROR", "Picture not load");
            }
        });
    }
    private void updateListViewSize(ListView lv){
        int totalHeight = 0;
        for(int i = 0; i < lv.getAdapter().getCount(); i++)
        {
            Log.wtf("index", i+"");
            View listItem = lv.getAdapter().getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();

        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (lv.getAdapter().getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();
        Log.wtf("ERROR", params.height+"");
    }

    @Override
    public void loadProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(this, RecipeSearchActivity.class);
        startActivity(intent);}
}
