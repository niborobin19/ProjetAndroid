package com.example.cuisinhelha.models;

public class Picture {
    private int idPicture;
    private int idRecipe;
    private String picture;

    public Picture() {
        this(-1, -1, "");
    }

    public Picture(int idRecipe, String picture) {
        this(-1, idRecipe, picture);
    }

    public Picture(int idPicture, int idRecipe, String picture) {
        this.idPicture = idPicture;
        this.idRecipe = idRecipe;
        this.picture = picture;
    }

    public int getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(int idPicture) {
        this.idPicture = idPicture;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "idPicture=" + idPicture +
                ", idRecipe=" + idRecipe +
                ", picture='" + picture + '\'' +
                '}';
    }
}
