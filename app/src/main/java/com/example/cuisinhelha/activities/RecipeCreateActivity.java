package com.example.cuisinhelha.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.cuisinhelha.R;

public class RecipeCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);
    }

    public void test(View v){
        Toast.makeText(this, "Well", Toast.LENGTH_SHORT).show();
    }
}