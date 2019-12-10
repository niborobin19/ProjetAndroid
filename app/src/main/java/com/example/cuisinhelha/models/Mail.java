package com.example.cuisinhelha.models;

public class Mail {
    private int userId;
    private String mail;

    public Mail() {
        this(-1, "");
    }

    public Mail(int userId, String mail) {
        this.userId = userId;
        this.mail = mail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "userId=" + userId +
                ", mail='" + mail + '\'' +
                '}';
    }
}


