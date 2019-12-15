package com.example.cuisinhelha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.helpers.UserPattern;
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;
import com.example.cuisinhelha.models.AnswerDB;
import com.example.cuisinhelha.models.AuthenticateUser;
import com.example.cuisinhelha.models.User;
import com.example.cuisinhelha.services.UserRepositoryService;
import com.example.cuisinhelha.utils.SHA256Hasher;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements IHeaderNavigation {
    private Button btnSignUp;
    private EditText etPseudo;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etMail;
    private TextView tvPseudoError;
    private TextView tvPasswordError;
    private TextView tvPasswordConfirmError;
    private TextView tvFirstNameError;
    private TextView tvLastNameError;
    private TextView tvMailError;
    private TextView tvUsed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.sign_up_btn_sign_up);

        etPseudo = findViewById(R.id.sign_up_et_pseudo);
        etPassword = findViewById(R.id.sign_up_et_password);
        etPasswordConfirm = findViewById(R.id.sign_up_et_password_confirm);
        etFirstName = findViewById(R.id.sign_up_et_first_name);
        etLastName = findViewById(R.id.sign_up_et_last_name);
        etMail = findViewById(R.id.sign_up_et_mail);
        tvUsed = findViewById(R.id.sign_up_tv_used);

        tvPseudoError = findViewById(R.id.sign_up_tv_pseudo_error);
        tvPasswordError = findViewById(R.id.sign_up_tv_password_error);
        tvPasswordConfirmError = findViewById(R.id.sign_up_tv_password_confirm_error);
        tvFirstNameError = findViewById(R.id.sign_up_tv_first_name_error);
        tvLastNameError = findViewById(R.id.sign_up_tv_last_name_error);
        tvMailError = findViewById(R.id.sign_up_tv_mail_error);

        tvPseudoError.setVisibility(View.INVISIBLE);
        tvPasswordError.setVisibility(View.INVISIBLE);
        tvPasswordConfirmError.setVisibility(View.INVISIBLE);
        tvFirstNameError.setVisibility(View.INVISIBLE);
        tvLastNameError.setVisibility(View.INVISIBLE);
        tvMailError.setVisibility(View.INVISIBLE);
        tvUsed.setVisibility(View.INVISIBLE);
    }


    private void updateTvError(TextView tv, String text, boolean visible) {
        int visibility = View.VISIBLE;
        if (!visible)
            visibility = View.INVISIBLE;

        tv.setVisibility(visibility);
        tv.setText(text);
    }

    public void onSignUpClick(View view) {
        tvUsed.setVisibility(View.INVISIBLE);
        if (!canSaveUser())
            return;

        saveNewUser();
    }

    private void saveNewUser() {
        final String pseudo = etPseudo.getText().toString();
        String password = etPassword.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String mail = etMail.getText().toString();

        final String hashedPassword = SHA256Hasher.hash(password);

        User user = new User(firstName, lastName, pseudo, mail, false, hashedPassword);

        UserRepositoryService.post(user).enqueue(new Callback<AnswerDB>() {
            @Override
            public void onResponse(Call<AnswerDB> call, Response<AnswerDB> response) {
                int code = response.code();
                if (response.body() != null) {
                    // On successfull sign up
                    if (code == 200) {
                        AuthenticateUser user = new AuthenticateUser(pseudo, hashedPassword);
                        UserRepositoryService.authenticate(user).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                int code = response.code();
                                // On successfull log in
                                if (code == 200) {
                                    // Put the user
                                    User user = response.body();
                                    putUser(user);

                                    // Sets the user token
                                    String token = response.body().getToken();
                                    MainActivity.setUserToken(token);
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {}
                        });

                        loadHomeActivity(null);
                    } else {
                    }
                } else {
                    tvUsed.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AnswerDB> call, Throwable t) {}
        });
    }

    private boolean canSaveUser() {
        boolean correct = true;

        if (!validatePseudo())
            correct = false;

        if (!validatePassword())
            correct = false;

        if (!validateFirstName())
            correct = false;

        if (!validateLastName())
            correct = false;

        if (!validateMail())
            correct = false;

        return correct;
    }

    private boolean validatePseudo() {
        String pseudo = etPseudo.getText().toString();

        if (pseudo.length() < 3) {
            updateTvError(tvPseudoError, "Too short.", true);
            return false;
        } else {
            tvPseudoError.setVisibility(View.INVISIBLE);
        }

        if (!UserPattern.validatePseudo(pseudo)) {
            updateTvError(tvPseudoError, "Not correct.", true);
            return false;
        } else {
            tvPseudoError.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    private boolean validatePassword() {
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirm.getText().toString();

        if (password.length() < 3) {
            updateTvError(tvPasswordError, "Too short.", true);
            return false;
        } else {
            tvPasswordError.setVisibility(View.INVISIBLE);
        }

        if (!password.equals(passwordConfirm)) {
            updateTvError(tvPasswordError, "Should match.", true);
            return false;
        } else {
            tvPasswordError.setVisibility(View.INVISIBLE);
        }

        if (!UserPattern.validatePassword(password)) {
            updateTvError(tvPasswordError, "Not correct.", true);
            return false;
        } else {
            tvPasswordError.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    private boolean validateFirstName() {
        String firstName = etFirstName.getText().toString();

        if (!UserPattern.validateName(firstName)) {
            updateTvError(tvFirstNameError, "Not correct.", true);
            return false;
        } else {
            tvFirstNameError.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    private boolean validateLastName() {
        String lastName = etLastName.getText().toString();

        if (!UserPattern.validateName(lastName)) {
            updateTvError(tvLastNameError, "Not correct.", true);
            return false;
        } else {
            tvLastNameError.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    private boolean validateMail() {
        String mail = etMail.getText().toString();

        if (mail.length() < 3) {
            updateTvError(tvMailError, "Too short.", true);
            return false;
        } else {
            tvMailError.setVisibility(View.INVISIBLE);
        }

        if (!UserPattern.validateMail(mail)) {
            updateTvError(tvMailError, "Not correct.", true);
            return false;
        } else {
            tvMailError.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    private void putUser(User user) {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        UserPreferences.putPreferences(user, editor);
        editor.apply();
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void loadHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(this, RecipeSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
