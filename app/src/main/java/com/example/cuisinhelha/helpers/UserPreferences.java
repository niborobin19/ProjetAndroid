package com.example.cuisinhelha.helpers;

import android.content.SharedPreferences;

import com.example.cuisinhelha.models.User;

import static android.content.Context.MODE_PRIVATE;

public class UserPreferences {
    public static final String PREFERENCES_NAME = "user";
    public static final String TOKEN = "token";
    public static final String FIRST_NAME = "firstName";
    public static final String PSEUDO = "pseudo";
    public static final String LAST_NAME = "lastName";
    public static final String MAIL = "mail";
    public static final String ID_USER = "idUser";
    public static final String USER_TYPE = "userType";

    public static void putUserPreferences(User user, SharedPreferences.Editor editor) {
        editor.putString(UserPreferences.TOKEN, user.getToken());
        editor.putString(UserPreferences.PSEUDO, user.getPseudo());
        editor.putString(UserPreferences.FIRST_NAME, user.getFirstName());
        editor.putString(UserPreferences.LAST_NAME, user.getLastName());
        editor.putString(UserPreferences.MAIL, user.getMail());
        editor.putInt(UserPreferences.ID_USER, user.getIdUser());
        editor.putBoolean(UserPreferences.USER_TYPE, user.isUserType());
    }
}
