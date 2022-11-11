package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class User {

    String id;
    String name;
    String surname;
    Partida partida_actual;
    List<Partida> partidas;
    List<GameResults> resultados;
    int actual_score;
    int actual_level;


    public User() {
//      this.id = RandomUtils.getId();
        this.partidas = new LinkedList<Partida>();
        this.resultados=new LinkedList<>();
    }

    public User(String id, String name, String surname) {
        this();
        this.setId(id);
        this.setSurname(surname);
        this.setName(name);
        this.resultados= new LinkedList<GameResults>();
        this.partidas =new LinkedList<Partida>();
        this.setActual_score(0);
        this.setActual_level(0);
    }

    public List<GameResults> getResultados() {
        return resultados;
    }

    public void setResultados(List<GameResults> resultados) {
        this.resultados = resultados;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public int getActual_score() {
        return actual_score;
    }

    public void setActual_score(int actual_score) {
        this.actual_score = actual_score;
    }
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Partida getPartida_actual() {
        return partida_actual;
    }
    public void setPartida_actual(Partida partida_actual) {
        this.partida_actual = partida_actual;
    }
    public int getActual_level() {
        return actual_level;
    }
    public void setActual_level(int actual_level) {
        this.actual_level = actual_level;
    }
    @Override
    public String toString() {
        return "User [id="+id+", name=" + name + ", surname=" + surname +",score= "+ actual_score +"]";
    }

    public Partida iniciarPartida(Partida t){
        if (partida_actual!=null){
            return null;
        }
        else {
            this.setPartida_actual(t);
            this.setActual_level(1);
            this.setActual_score(50);
            this.resultados.add(new GameResults(t.getId(),this.getActual_score()));
            return t;
        }
    }


    public LevelResults pasarLevel(int score,String date){
        if(this.getPartida_actual()!=null) {
            if (this.getActual_level() + 1 <= this.getPartida_actual().num_levels) {
                this.setActual_level(this.getActual_level()+1);
                this.setActual_score(this.getActual_score()+score);
                LevelResults completado = new LevelResults(this.getPartida_actual().getId(), this.getActual_level(), score, date);
                GameResults esto = this.getGameResults(this.partida_actual.getId());
                esto.getResultados().add(completado);
                esto.aumentar_score(score);
                return completado;
            } else {
                LevelResults completado = new LevelResults(this.getPartida_actual().getId(), this.getActual_level(), score, date);
                GameResults esto = this.getGameResults(this.partida_actual.getId());
                esto.getResultados().add(completado);
                esto.setFinal_score(100);
                this.acabarPartida();
                return completado;
            }
        }
        else {
            return null;
        }
    }
    public Partida acabarPartida(){
        if(this.getPartida_actual()==null) {
            Partida acabada = this.getPartida_actual();
            this.setPartida_actual(null);
            this.partidas.add(acabada);
            return acabada;
        }
        else {
            return null;
        }
    }
    public GameResults getGameResults(String id_partida){
        for (GameResults t: this.resultados) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public int gastarDsa_coins(int precio){
        int act=this.getActual_score();
        int desp= act-precio;
        if (desp>0){
            this.setActual_score(desp);
            return 0;
        }
        else {
            this.setActual_score(act);
            return -1;
        }
    }
    public Partida compraObjeto(Partida t){
        int aff = gastarDsa_coins(t.getNum_levels());
        if(aff==0){
            this.partidas.add(t);
            return t;
        }
        else {
            return null;
        }
    }

}