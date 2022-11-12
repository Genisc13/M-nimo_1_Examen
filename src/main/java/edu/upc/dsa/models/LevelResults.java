package edu.upc.dsa.models;

public class LevelResults {



    String id_partida;
    int level;
    int points;
    String date;

    public LevelResults(){
    }
    public LevelResults(String id_partida, int level, int points, String date) {
        this.id_partida = id_partida;
        this.level = level;
        this.points = points;
        this.date = date;
    }

    public String getId_partida() {
        return id_partida;
    }

    public void setId_partida(String id_partida) {
        this.id_partida = id_partida;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User [id="+id_partida+", level=" + level + ", num_points=" + points+", date= "+ date +"]";
    }

}
