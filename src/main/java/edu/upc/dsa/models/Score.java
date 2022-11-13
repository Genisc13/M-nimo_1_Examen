package edu.upc.dsa.models;

public class Score {
    int score;
    public Score(){
        this.setScore(0);
    }
    public Score(int score) {
        this.setScore(score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
