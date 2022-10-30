package edu.upc.dsa.models;

public class Objeto {

    String name;
    String description;
    int coins;

    public Objeto(){
        this.setName("");
        this.setDescription("");
        this.setCoins(0);
    }
    public Objeto(String name, String description, int coins) {
        this.setName(name);
        this.setDescription(description);
        this.setCoins(coins);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

}
