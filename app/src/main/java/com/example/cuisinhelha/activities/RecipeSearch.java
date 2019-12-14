package com.example.cuisinhelha.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.adapters.RecipeSearchResultAdapter;
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.services.RecipeRepositoryService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeSearch extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String EXTRA_SEARCH_ACTIVITY = "EXTRA_SEARCH_ACTIVITY";

    private EditText etSearch;
    private ListView lvResult;
    private RecipeSearchResultAdapter adapter;

    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        recipes = new ArrayList<>();
        recipes.add(new Recipe(1, 1, "mock", "19-12-2019", "mock summary", 3 ,150, 3, "Dessert", "ElsaD"));

        etSearch = findViewById(R.id.searchEt);
        lvResult = findViewById(R.id.resultLv);

        adapter = new RecipeSearchResultAdapter(this, R.id.resultLv, recipes);
        lvResult.setAdapter(adapter);
        lvResult.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, RecipeDetail.class);
        intent.putExtra(EXTRA_SEARCH_ACTIVITY, recipes.get(position).getIdRecipe());
        startActivity(intent);


    }

    public void searchRecipes(View view) {
        RecipeRepositoryService.queryText(etSearch.getText().toString())
                .enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        recipes.clear();
                        recipes.addAll(response.body());
                        //Log.wtf("recipes", recipes.toString());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        Log.wtf("SuperError", "Une erreur lors de l'accès à la table 'recipe' est survenue");
                    }
                });
    }
}
