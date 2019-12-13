package com.example.cuisinhelha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;

public class ProfileActivity extends AppCompatActivity implements IHeaderNavigation {
    private ImageView ivProfile;
    private TextView tvName;
    private EditText etMail;
    private EditText etMailConfirm;
    private EditText etPassword;
    private EditText etPasswordConfirm;

    private boolean isLoggingOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfile = findViewById(R.id.iv_profile_icon);
        tvName = findViewById(R.id.tv_user_name_value);
        etMail = findViewById(R.id.et_user_mail);
        etMailConfirm = findViewById(R.id.et_user_mail_confirm);
        etPassword = findViewById(R.id.et_user_password);
        etPasswordConfirm = findViewById(R.id.et_user_password_confirm);

        // If there's no token stored, send the user to the HomeActivity.
        SharedPreferences pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        if(!UserPreferences.isConnected(pref)){
            loadMainActivity();
        }

        String fullName = pref.getString(UserPreferences.FIRST_NAME, "") + " " + pref.getString(UserPreferences.LAST_NAME, "");
        String mail = pref.getString(UserPreferences.MAIL, "");


        tvName.setText(fullName);

        etMail.setText(mail);
        etMail.setEnabled(false);
        etMailConfirm.setVisibility(View.INVISIBLE);

        etPassword.setEnabled(false);
        etPasswordConfirm.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If there's no token stored, send the user to the HomeActivity.
        SharedPreferences pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        if(!UserPreferences.isConnected(pref)){
            loadMainActivity();
        }
        ivProfile.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!isLoggingOut)
            ivProfile.setVisibility(View.VISIBLE);
    }

    public void logOut(View view) {
        isLoggingOut = true;
        SharedPreferences preferences = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        UserPreferences.clearPreferences(preferences);

        loadMainActivity();
    }

    public void loadMainActivity(){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadHomeActivity(View view) {
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(ProfileActivity.this, RecipeSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadProfileActivity(View view) {}
}
