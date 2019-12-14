package com.example.cuisinhelha.models;

public class Step {
    private int idStep;
    private int idRecipe;
    private int stepNumber;
    private String step;

    public Step() {
        this(-1, -1, -1, "");
    }

    public Step(int idRecipe, int stepNumber, String step) {
        this(-1, idRecipe, stepNumber, step);
    }

    public Step(int idStep, int idRecipe, int stepNumber, String step) {
        this.idStep = idStep;
        this.idRecipe = idRecipe;
        this.stepNumber = stepNumber;
        this.step = step;
    }

    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Step{" +
                "idStep=" + idStep +
                ", idRecipe=" + idRecipe +
                ", stepNumber=" + stepNumber +
                ", step='" + step + '\'' +
                '}';
    }
}
