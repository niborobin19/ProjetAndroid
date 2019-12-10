package com.example.cuisinhelha.models;

public class Ingredient {
    private int idIngredient;
    private int idRecipe;
    private int quantity;
    private String unit;
    private String nameIngredient;

    public Ingredient() {
        this(-1, -1, -1, "", "");
    }

    public Ingredient(String nameIngredient) {
        this(-1, -1, 0, "", nameIngredient);
    }

    public Ingredient(int idIngredient, int quantity, String unit, String nameIngredient) {
        this(idIngredient, -1, quantity, unit, nameIngredient);
    }

    public Ingredient(int idIngredient, int idRecipe, int quantity, String unit, String nameIngredient) {
        this.idIngredient = idIngredient;
        this.idRecipe = idRecipe;
        this.quantity = quantity;
        this.unit = unit;
        this.nameIngredient = nameIngredient;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", idRecipe=" + idRecipe +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", nameIngredient='" + nameIngredient + '\'' +
                '}';
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNameIngredient() {
        return nameIngredient;
    }

    public void setNameIngredient(String nameIngredient) {
        this.nameIngredient = nameIngredient;
    }


}
