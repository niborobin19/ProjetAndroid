package com.example.cuisinhelha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;

public class HomeActivity extends AppCompatActivity implements IHeaderNavigation {
    private Button btnAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAddRecipe = findViewById(R.id.createRecipeBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        if(!UserPreferences.isConnected(pref)){
            btnAddRecipe.setVisibility(View.INVISIBLE);
        } else {
            btnAddRecipe.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadProfileActivity(View view){
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, RecipeSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadHomeActivity(View view) {}


    public void loadRecipeCreateActivity(View view) {
        Intent intent = new Intent(this, RecipeCreateActivity.class);
        startActivity(intent);
    }
}
