package com.example.cuisinhelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void test(View v){
        Toast.makeText(MainActivity.this, "Well", Toast.LENGTH_SHORT).show();
    }

    public void goRecipeDetail(View view) {

        Intent intent = new Intent(MainActivity.this, RecipeDetail.class);
        startActivity(intent);


    }
}
