package com.example.cuisinhelha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.adapters.UserRecipeAdapter;
import com.example.cuisinhelha.adapters.UserReviewAdaper;
import com.example.cuisinhelha.helpers.UserPattern;
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;
import com.example.cuisinhelha.models.MailUser;
import com.example.cuisinhelha.models.PasswordUser;
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.models.Review;
import com.example.cuisinhelha.services.RecipeRepositoryService;
import com.example.cuisinhelha.services.ReviewRepositoryService;
import com.example.cuisinhelha.services.UserRepositoryService;
import com.example.cuisinhelha.utils.SHA256Hasher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements IHeaderNavigation {
    private static final String EXTRA_PROFILE_ACTIVITY = "EXTRA_SEARCH_ACTIVITY";

    private SharedPreferences pref;

    private ImageView ivProfile;
    private TextView tvName;
    private TextView tvError;
    private TextView tvSuccess;
    private EditText etOldPassword;
    private EditText etMail;
    private EditText etMailConfirm;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private ImageButton ibPasswordEdit;
    private ImageButton ibPasswordSave;
    private ImageButton ibMailEdit;
    private ImageButton ibMailSave;

    private boolean isNavigating;

    private String newMail;

    private List<Recipe> recipes;
    private ListView lvRecipe;
    private UserRecipeAdapter recipeAdapter;

    private List<Review> reviews;
    private ListView lvReview;
    private UserReviewAdaper reviewAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfile = findViewById(R.id.header_iv_profile_icon);
        tvName = findViewById(R.id.profile_tv_user_name_value);
        tvError = findViewById(R.id.profile_tv_error);
        tvSuccess = findViewById(R.id.profile_tv_success);
        etOldPassword = findViewById(R.id.profile_et_user_old_password);
        etMail = findViewById(R.id.profile_et_user_mail);
        etMailConfirm = findViewById(R.id.profile_et_user_mail_confirm);
        etPassword = findViewById(R.id.profile_et_user_password);
        etPasswordConfirm = findViewById(R.id.profile_et_user_password_confirm);
        ibPasswordEdit = findViewById(R.id.profile_ib_edit_password);
        ibPasswordSave = findViewById(R.id.profile_ib_save_password);
        ibMailEdit = findViewById(R.id.profile_ib_edit_mail);
        ibMailSave = findViewById(R.id.profile_ib_save_mail);


        // If there's no token stored, send the user to the HomeActivity.
        pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        if (!UserPreferences.isConnected(pref)) {
            loadMainActivity();
        }

        recipes = new ArrayList<>();
        reviews = new ArrayList<>();

        lvRecipe = findViewById(R.id.user_recipe_lv);
        lvReview = findViewById(R.id.user_review_lv);

        recipeAdapter = new UserRecipeAdapter(this, R.id.user_recipe_lv, recipes);
        reviewAdaper = new UserReviewAdaper(this, R.id.user_review_lv, reviews);

        lvRecipe.setAdapter(recipeAdapter);
        lvRecipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadDetailsActivity(recipes.get(position).getIdRecipe());
            }
        });
        lvReview.setAdapter(reviewAdaper);
        lvReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadDetailsActivity(reviews.get(position).getIdRecipe());
            }
        });

        loadRecipes();
        loadReviews();


        String fullName = pref.getString(UserPreferences.FIRST_NAME, "") +
                " '" + pref.getString(UserPreferences.PSEUDO, "") + "' " +
                pref.getString(UserPreferences.LAST_NAME, "");
        String mail = pref.getString(UserPreferences.MAIL, "");


        tvName.setText(fullName);
        tvError.setVisibility(View.INVISIBLE);
        tvSuccess.setVisibility(View.INVISIBLE);

        etMail.setText(mail);
        etMail.setEnabled(false);
        etMailConfirm.setVisibility(View.INVISIBLE);

        etPassword.setEnabled(false);
        etPassword.setVisibility(View.INVISIBLE);
        etPasswordConfirm.setVisibility(View.INVISIBLE);

        ibMailSave.setEnabled(false);
        ibPasswordSave.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If there's no token stored, send the user to the HomeActivity.
        SharedPreferences pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        if (!UserPreferences.isConnected(pref)) {
            loadMainActivity();
        }
        ivProfile.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isNavigating)
            ivProfile.setVisibility(View.VISIBLE);
    }

    public void logOut(View view) {
        isNavigating = true;
        SharedPreferences preferences = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        UserPreferences.clearPreferences(preferences);

        loadMainActivity();
    }


    public void onEditMailClick(View view) {
        setEditingMail(true);
    }

    public void saveNewMail(View view) {
        cleanTvResult();

        String mail = etMail.getText().toString();
        String mailConfirm = etMailConfirm.getText().toString();

        int idUser = getIdUser();
        if (!canChangeMail(mail, mailConfirm, idUser))
            return;

        updateTvError("", false);
        MailUser user = new MailUser(idUser, mail);
        newMail = user.getMail();

        // Try to put the new mail
        UserRepositoryService.putMail(user).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                boolean error = false;

                // Updates the view
                if (response.body() != null) {
                    if (response.body().booleanValue()) {
                        updateTvSuccess("Mail successfully updated.", true);
                        etMail.setText(newMail);
                    } else {
                        error = true;
                    }
                } else {
                    error = true;
                }

                if (error) {
                    updateTvError("Error, please try later.", true);
                }

                resetMailForm();

                // Updates the mail preference
                SharedPreferences pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(UserPreferences.MAIL, newMail);
                editor.commit();

                newMail = "";
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                resetMailForm();
                updateTvError("Error, please try later.", true);
            }
        });
    }

    private void resetMailForm() {
        setEditingMail(false);
        etMailConfirm.setText("");
    }


    public boolean canChangeMail(String mail, String mailConfirm, int idUser) {
        if (!canContinue(idUser))
            return false;

        // Verify mails are equals
        if (!mail.equals(mailConfirm)) {
            updateTvError("Mails are different.", true);
            return false;
        }

        // Verify the mail respect pattern
        if (!UserPattern.validateMail(mail)) {
            updateTvError("Invalid mail address.", true);
            return false;
        }

        return true;
    }

    private void setEditingMail(boolean edit) {
        etMail.setEnabled(edit);
        etMailConfirm.setEnabled(edit);
        ibMailSave.setEnabled(edit);
        ibMailEdit.setEnabled(!edit);

        if (edit) {
            etMailConfirm.setVisibility(View.VISIBLE);
            ibMailEdit.setVisibility(View.INVISIBLE);
        } else {
            etMailConfirm.setVisibility(View.INVISIBLE);
            ibMailEdit.setVisibility(View.VISIBLE);
        }
    }


    public void onEditPswClick(View view) {
        cleanTvResult();
        String oldPassword = etOldPassword.getText().toString();
        if (!UserPattern.validatePassword(oldPassword)) {
            updateTvError("Invalid old password.", true);
        } else if (oldPassword.length() < 3) {
            updateTvError("Enter your old password first.", true);
        } else {
            setEditingPassword(true);
        }
    }

    public void saveNewPassword(View view) {
        cleanTvResult();

        String oldPassword = etOldPassword.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirm.getText().toString();

        int idUser = getIdUser();
        if (!canChangePassword(password, passwordConfirm, idUser))
            return;

        updateTvError("", false);

        String hashedOldPassword = SHA256Hasher.hash(oldPassword);
        String hashedPassword = SHA256Hasher.hash(password);

        final PasswordUser user = new PasswordUser(idUser, hashedPassword, hashedOldPassword);
        UserRepositoryService.putPassword(user).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                boolean error = false;

                Log.wtf("raw", response.raw().toString());
                Log.wtf("code", "" + response.code());
                if (response.code() == 400) {
                    updateTvError("Wrong old password.", true);
                } else if (response.body() != null) {
                    if (response.body().booleanValue())
                        updateTvSuccess("Password successfully updated.", true);
                    else
                        error = true;
                } else {
                    error = true;
                }

                if (error)
                    updateTvError("Error, please try later.", true);

                resetPasswordForm();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                resetPasswordForm();
                updateTvError("Error, please try later.", true);
            }
        });


    }

    private void resetPasswordForm() {
        setEditingPassword(false);
        etOldPassword.setText("");
        etPassword.setText("");
        etPasswordConfirm.setText("");
    }

    public boolean canChangePassword(String password, String passwordConfirm, int idUser) {
        if (!canContinue(idUser))
            return false;

        if (password.length() < 3) {
            updateTvError("Password too short.", true);
            return false;
        }

        // Verify that mails are equals
        if (!password.equals(passwordConfirm)) {
            updateTvError("Passwords are different.", true);
            return false;
        }

        // Verify the mail respect pattern
        if (!UserPattern.validatePassword(password)) {
            updateTvError("This isn't a valid mail password.", true);
            return false;
        }

        return true;
    }

    private void setEditingPassword(boolean edit) {
        etPassword.setEnabled(edit);
        etPasswordConfirm.setEnabled(edit);
        ibPasswordSave.setEnabled(edit);
        ibPasswordEdit.setEnabled(!edit);

        if (edit) {
            etPassword.setVisibility(View.VISIBLE);
            etPasswordConfirm.setVisibility(View.VISIBLE);
            ibPasswordEdit.setVisibility(View.INVISIBLE);
            etOldPassword.setVisibility(View.INVISIBLE);
        } else {
            etPassword.setVisibility(View.INVISIBLE);
            etPasswordConfirm.setVisibility(View.INVISIBLE);
            ibPasswordEdit.setVisibility(View.VISIBLE);
            etOldPassword.setVisibility(View.VISIBLE);
        }
    }

    private int getIdUser() {
        SharedPreferences pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);
        return pref.getInt(UserPreferences.ID_USER, -1);
    }

    private boolean canContinue(int idUser) {
        if (idUser < 0) {
            updateTvError("You have to be online to do that.", true);
            loadMainActivity();
            UserPreferences.clearPreferences(getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE));
            return false;
        }
        return true;
    }

    private void cleanTvResult() {
        updateTvError("", false);
        updateTvSuccess("", false);
    }

    private void updateTvError(String text, boolean visible) {
        int visibility = (visible) ? View.VISIBLE : View.INVISIBLE;
        tvError.setText(text);
        tvError.setVisibility(visibility);
    }

    private void updateTvSuccess(String text, boolean visible) {
        int visibility = (visible) ? View.VISIBLE : View.INVISIBLE;
        tvSuccess.setText(text);
        tvSuccess.setVisibility(visibility);
    }

    public void loadMainActivity() {
        isNavigating = true;
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadHomeActivity(View view) {
        isNavigating = true;
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadRecipeSearchActivity(View view) {
        isNavigating = true;
        Intent intent = new Intent(ProfileActivity.this, RecipeSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadProfileActivity(View view) {
    }

    public void loadDetailsActivity(int id)
    {
        Intent intent = new Intent(this, RecipeDetail.class);
        intent.putExtra(EXTRA_PROFILE_ACTIVITY, id);
        startActivity(intent);
    }

    public void loadRecipes()
    {
        RecipeRepositoryService.queryByUser(pref.getInt(UserPreferences.ID_USER, -1)).enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeAdapter.addAll(response.body());
                updateListViewSize(lvRecipe);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.wtf("query", "une erreur est survenue lors de la connection à la table recipes");
            }
        });
    }

    public void loadReviews()
    {
        ReviewRepositoryService.queryByUser(pref.getInt(UserPreferences.ID_USER, -1)).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                reviewAdaper.addAll(response.body());
                updateListViewSize(lvReview);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.wtf("query", "une erreur est survenue lors de la connection à la table reviews");
            }
        });
    }

    private void updateListViewSize(ListView lv){
        int totalHeight = 0;
        for(int i = 0; i < lv.getAdapter().getCount(); i++)
        {
            View listItem = lv.getAdapter().getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (lv.getAdapter().getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();
    }
}
