package edu.upc.dsa.models;

public class Level {
    int level;
    public Level(){
        this.setLevel(0);
    }

    public Level(int level) {
        this.setLevel(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
