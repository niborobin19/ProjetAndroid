package com.example.cuisinhelha.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cuisinhelha.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(this, RecipeSearch.class);
        startActivity(intent);
    }

    public void loadRecipeCreateActivity(View view){
        Intent intent = new Intent(this, RecipeCreate.class);
        startActivity(intent);
    }
}
