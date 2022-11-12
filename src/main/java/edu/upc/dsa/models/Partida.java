package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Partida {
    String id;
    String description;
    int num_levels;
    List<User> usuarios;

    public Partida(){
        this.setId("");
        this.setDescription("");
        this.setNum_levels(0);
        this.usuarios=new LinkedList<>();
    }
    public Partida(String id, String description, int num_levels) {
        this.setId(id);
        this.setDescription(description);
        this.setNum_levels(num_levels);
        this.usuarios=new LinkedList<>();
    }
    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNum_levels() {
        return num_levels;
    }

    public void setNum_levels(int num_levels) {
        this.num_levels = num_levels;
    }

    @Override
    public String toString() {
        return "User [id="+id+", description=" + description + ", num_levels=" + num_levels +"]";
    }

}
