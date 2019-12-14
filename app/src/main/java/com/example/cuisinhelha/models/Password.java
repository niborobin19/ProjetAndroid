package com.example.cuisinhelha.models;

import retrofit2.Retrofit;

public class Password {
    private int userId;
    private String passwordNew;
    private String passwordOld;

    public Password() {
        this(-1, "", "");
    }

    public Password(int userId, String passwordNew, String passwordOld) {
        this.userId = userId;
        this.passwordNew = passwordNew;
        this.passwordOld = passwordOld;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    @Override
    public String toString() {
        return "Password{" +
                "userId=" + userId +
                ", passwordNew='" + passwordNew + '\'' +
                ", passwordOld='" + passwordOld + '\'' +
                '}';
    }
}
