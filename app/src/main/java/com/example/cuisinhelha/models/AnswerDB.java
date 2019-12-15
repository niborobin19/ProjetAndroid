package com.example.cuisinhelha.models;

public class AnswerDB extends User{
    private String message;

    public AnswerDB(){
        message = "empty";
    }

    public AnswerDB(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AnswerDB{" +
                "message='" + message + '\'' +
                '}';
    }
}
