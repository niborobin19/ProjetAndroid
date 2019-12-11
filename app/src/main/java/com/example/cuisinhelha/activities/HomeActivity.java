package com.example.cuisinhelha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.activities.MainActivity;
import com.example.cuisinhelha.helpers.UserPreferences;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void logOut(View view) {
        SharedPreferences preferences = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
