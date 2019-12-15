package com.example.cuisinhelha.models;

public class PasswordUser {
    public int userID;
    public String passwordNew;
    public String passwordOld;

    public PasswordUser(int idUser, String passwordNew, String passwordOld) {
        this.userID = idUser;
        this.passwordNew = passwordNew;
        this.passwordOld = passwordOld;
    }
}
