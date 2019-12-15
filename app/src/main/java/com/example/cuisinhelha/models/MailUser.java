package com.example.cuisinhelha.models;

public class MailUser {
    private int userID;
    private String mail;

    public MailUser(int userID, String mail) {
        this.userID = userID;
        this.mail = mail;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
