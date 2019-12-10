package com.example.cuisinhelha.models;

public class Review {
    private int idUser;
    private int idRecipe;
    private int rate;
    private String reviewMessage;
    private String pseudo;
    private String nameRecipe;

    public Review() {
        this(-1, -1, 0, "", "", "");
    }

    public Review(int idUser, int idRecipe, int rate, String reviewMessage) {
        this(idUser, idRecipe, rate, reviewMessage, "", "");
    }

    public Review(int idUser, int idRecipe, int rate, String reviewMessage, String nameRecipe) {
        this(idUser, idRecipe, rate, reviewMessage, "", nameRecipe);
    }

    public Review(int idUser, int idRecipe, int rate, String reviewMessage, String pseudo, String nameRecipe) {
        this.idUser = idUser;
        this.idRecipe = idRecipe;
        this.rate = rate;
        this.reviewMessage = reviewMessage;
        this.pseudo = pseudo;
        this.nameRecipe = nameRecipe;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }

    @Override
    public String toString() {
        return "Review{" +
                "idUser=" + idUser +
                ", idRecipe=" + idRecipe +
                ", rate=" + rate +
                ", reviewMessage='" + reviewMessage + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", nameRecipe='" + nameRecipe + '\'' +
                '}';
    }
}
