package com.example.majkiel.jumpproject.Objects;

/**
 * Created by mjbor on 8/7/2017.
 */

public class Highscore {
    private int score;
    private String name;
    private String date;

    public Highscore(){

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Highscore(int score, String name, String date) {
        this.score = score;
        this.name = name;
        this.date = date;
    }
}
