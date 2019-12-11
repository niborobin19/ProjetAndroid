package com.example.cuisinhelha.models;

public class Step {
    private int idStep;
    private int idRecipe;
    private int stepNb;
    private String step;

    public Step() {
        this(-1, -1, -1, "");
    }

    public Step(int idRecipe, int stepNb, String step) {
        this(-1, idRecipe, stepNb, step);
    }

    public Step(int idStep, int idRecipe, int stepNb, String step) {
        this.idStep = idStep;
        this.idRecipe = idRecipe;
        this.stepNb = stepNb;
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

    public int getStepNb() {
        return stepNb;
    }

    public void setStepNb(int stepNb) {
        this.stepNb = stepNb;
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
                ", stepNb=" + stepNb +
                ", step='" + step + '\'' +
                '}';
    }
}
