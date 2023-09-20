package com.example.app_hk;

public class User {
    private String email;
    private int score;

    // Required default constructor for Firebase
    public User() {
    }

    public User(String email, int score) {
        this.email = email;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
