package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.LinkedList;
import java.util.List;

public class User {

    String id;
    String name;
    String surname;
    String birthday;
    Credentials credentials;
    int dsa_coins;
    List<Objeto> compras;

    public User() {
//        this.id = RandomUtils.getId();
        this.compras= new LinkedList<Objeto>();
    }

    public User(String id,String name, String surname, String birthday, String electronic, String password) {
        this();
        this.setId(id);
        this.setSurname(surname);
        this.setName(name);
        this.setBirthday(birthday);
        this.setCredentials(new Credentials(electronic,password));
        this.setDsa_coins(50);
        this.compras=new LinkedList<Objeto>();
    }
    public List<Objeto> getCompras() {
        return compras;
    }

    public void setCompras(List<Objeto> compras) {
        this.compras = compras;
    }

    public int getDsa_coins() {
        return dsa_coins;
    }

    public void setDsa_coins(int dsa_coins) {
        this.dsa_coins = dsa_coins;
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
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }


    @Override
    public String toString() {
        return "User [id="+id+", name=" + name + ", surname=" + surname +", birthday="+ birthday +",coins= "+dsa_coins+"]";
    }

    public int gastarDsa_coins(int precio){
        int act=this.getDsa_coins();
        int desp= act-precio;
        if (desp>0){
            this.setDsa_coins(desp);
            return 0;
        }
        else {
            this.setDsa_coins(act);
            return -1;
        }


    }
    public Objeto compraObjeto(Objeto t){
        int aff = gastarDsa_coins(t.getCoins());
        if(aff==0){
            this.compras.add(t);
            return t;
        }
        else {
            return null;
        }
    }

}