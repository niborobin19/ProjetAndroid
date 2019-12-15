package com.example.cuisinhelha.models;

import java.util.regex.Pattern;

public class User {
    private int idUser;
    private String firstName;
    private String lastName;
    private String pseudo;
    private String mail;
    private boolean userType;
    private String token;
    private String password;

    public User() {
        this(-1, "", "", "", "", false, "", "");
    }

    public User(String firstName, String lastName, String pseudo, String mail, boolean userType, String password) {
        this(-1, firstName, lastName, pseudo, mail, userType, "", password);
    }

    public User(int idUser, String firstName, String lastName, String pseudo, String mail, boolean userType, String token) {
        this(idUser, firstName, lastName, pseudo, mail, userType, token, "");
    }

    public User(int idUser, String firstName, String lastName, String pseudo, String mail, boolean userType, String token, String password) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudo = pseudo;
        this.mail = mail;
        this.userType = userType;
        this.token = token;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", mail='" + mail + '\'' +
                ", userType=" + userType +
                ", token='" + token + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
