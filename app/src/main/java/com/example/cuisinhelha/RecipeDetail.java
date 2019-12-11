package com.example.cuisinhelha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.services.RecipeRepositoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetail extends AppCompatActivity {

    private TextView TV_recipeTitle;
    private Recipe currentRecipe = new Recipe();
    //1,"NAME","t", "t", 1, 1, "Dessert"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        TV_recipeTitle = findViewById(R.id.recipeTitleTV);
        TV_recipeTitle.setText(currentRecipe.getNameRecipe());
        Log.wtf("", "test");

    }

    public void recipDetails(View view){

        RecipeRepositoryService.queryByRecipe(1).enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.i("Recipe", response.body().toString());
                currentRecipe = (Recipe) response.body();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

    }

}
