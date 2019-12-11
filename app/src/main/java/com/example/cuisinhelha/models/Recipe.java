package com.example.cuisinhelha.models;

public class Recipe {
    private int idRecipe;
    private int idUser;
    private String nameRecipe;
    private String postDate;
    private String summary;
    private int persons;
    private int spiceRate;
    private String recipeType;
    private String pseudo;

    public Recipe(int idRecipe, int idUser, String nameRecipe, String postDate, String summary, int persons, int spiceRate, String recipeType) {
        this(idRecipe, idUser, nameRecipe, postDate, summary, persons, spiceRate, recipeType, "");
    }

    public Recipe(int idUser, String nameRecipe, String postDate, String summary, int persons, int spiceRate, String recipeType, String pseudo) {
        this(-1, idUser, nameRecipe, postDate, summary, persons, spiceRate, recipeType, pseudo);
    }

    public Recipe(int idUser, String nameRecipe, String postDate, String summary, int persons, int spiceRate, String recipeType) {
        this(-1, idUser, nameRecipe, postDate, summary, persons, spiceRate, recipeType, "");
    }

    public Recipe() {

    }

    public Recipe(int idRecipe, int idUser, String nameRecipe, String postDate, String summary, int persons, int spiceRate, String recipeType, String pseudo) {
        this.idRecipe = idRecipe;
        this.idUser = idUser;
        this.nameRecipe = nameRecipe;
        this.postDate = postDate;
        this.summary = summary;
        this.persons = persons;
        this.spiceRate = spiceRate;
        this.recipeType = recipeType;
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "idRecipe=" + idRecipe +
                ", idUser=" + idUser +
                ", nameRecipe='" + nameRecipe + '\'' +
                ", postDate='" + postDate + '\'' +
                ", summary='" + summary + '\'' +
                ", persons=" + persons +
                ", spiceRate=" + spiceRate +
                ", recipeType='" + recipeType + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public int getSpiceRate() {
        return spiceRate;
    }

    public void setSpiceRate(int spiceRate) {
        this.spiceRate = spiceRate;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
