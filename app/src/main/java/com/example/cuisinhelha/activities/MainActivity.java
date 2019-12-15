package com.example.cuisinhelha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;
import com.example.cuisinhelha.models.AuthenticateUser;
import com.example.cuisinhelha.models.User;
import com.example.cuisinhelha.services.UserRepositoryService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IHeaderNavigation {
    private EditText etPseudo;
    private EditText etPassword;
    private TextView tvError;
    private ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the user account preferences
        SharedPreferences preferences = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        String token = preferences.getString("token", null);
        // If there is a token, sends the user to the HomeActivity
        if (token != null) {
            setUserToken(token);
            loadHomeActivity(null);
        }

        etPseudo = findViewById(R.id.et_pseudo);
        etPassword = findViewById(R.id.et_password);
        tvError = findViewById(R.id.tv_error);
        pbLogin = findViewById(R.id.main_pb_login);

        pbLogin.setVisibility(View.INVISIBLE);
    }

    public void authenticate(View v) {

        AuthenticateUser user = new AuthenticateUser(etPseudo.getText().toString(), etPassword.getText().toString());
        if (UserRepositoryService.canAuthenticate(user)) {
            pbLogin.setVisibility(View.VISIBLE);
            UserRepositoryService.hashAndAuthenticate(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    int code = response.code();
                    // On successfull log in
                    if (code == 200) {
                        updateTvError("", false);

                        // Put the user
                        User user = response.body();
                        putUser(user);

                        // Sets the user token
                        String token = response.body().getToken();
                        setUserToken(token);

                        loadHomeActivity(null);

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
                    pbLogin.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    onAuthenticateError();
                    pbLogin.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            updateTvError("Invalid credentials.", true);
        }

    }

    private void putUser(User user) {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        UserPreferences.putPreferences(user, editor);
        editor.apply();
    }

    private void updateTvError(String msg, boolean visible) {
        int visibilityID = -1;
        if (visible)
            visibilityID = View.VISIBLE;
        else
            visibilityID = View.INVISIBLE;

        tvError.setText(msg);
        tvError.setVisibility(visibilityID);
    }

    private void onAuthenticateError() {
        String error = "Error, please try later.";
        updateTvError(error, true);
    }

    private void setUserToken(String token) {
        UserRepositoryService.USER_TOKEN = "Bearer " + token;
    }


    public void onAnonymousBtnClick(View view) {
        loadHomeActivity(null);
    }

    @Override
    public void loadHomeActivity(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(this, RecipeSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadProfileActivity(View view) {
//        Intent intent = new Intent(this, ProfileActivity.class);
//        startActivity(intent);
    }

    public void loadRecipeCreateActivity(View view) {
        Intent intent = new Intent(this, RecipeCreateActivity.class);
        startActivity(intent);
    }
}