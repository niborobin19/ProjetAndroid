package com.example.cuisinhelha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.models.AuthenticateUser;
import com.example.cuisinhelha.models.User;
import com.example.cuisinhelha.services.UserRepositoryService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText etPseudo;
    private EditText etPassword;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the user account preferences
        SharedPreferences preferences = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        String token = preferences.getString("token", null);
        // If there is a token, sends the user to the HomeActivity
        if (token != null) {
//            goToHomeActivity();
        }

        etPseudo = findViewById(R.id.et_pseudo);
        etPassword = findViewById(R.id.et_password);
        tvError = findViewById(R.id.tv_error);
    }

    public void authenticate(View v) {
        AuthenticateUser user = new AuthenticateUser(etPseudo.getText().toString(), etPassword.getText().toString());
        UserRepositoryService.hashAndAuthenticate(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int code = response.code();
                // On successfull connexion
                if (code == 200) {
                    updateTvError("", false);

                    User user = response.body();

                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    UserPreferences.putUserPreferences(user, editor);
                    editor.apply();

                    goToHomeActivity();

                    // On wrong credentials
                } else if (code == 400) {
                    try {
                        String error = response.errorBody().string();
                        String errorMessage = error.substring(12, error.indexOf(".") + 1);
                        updateTvError(errorMessage, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // On other HTTP answer
                } else {
                    onAuthenticateError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onAuthenticateError();
            }
        });
    }

    public void updateTvError(String msg, boolean visible) {
        int visibilityID = -1;
        if (visible)
            visibilityID = View.VISIBLE;
        else
            visibilityID = View.INVISIBLE;

        tvError.setText(msg);
        tvError.setVisibility(visibilityID);
    }

    public void onAuthenticateError() {
        String error = "Error, please try later.";
        updateTvError(error, true);
    }

    public void onAnonymousBtnClick(View view) {
        goToHomeActivity();
    }

    public void goToHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(this, RecipeSearchActivity.class);
        startActivity(intent);
    }

    public void loadRecipeCreateActivity(View view) {
        Intent intent = new Intent(this, RecipeCreateActivity.class);
        startActivity(intent);
    }
}