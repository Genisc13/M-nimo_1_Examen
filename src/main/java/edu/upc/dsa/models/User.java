package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class User {

    String id;
    String name;
    String surname;
    String birthday;
    String electronic;
    String password;
    static int lastId;

    public User() {
        this.id = RandomUtils.getId();
    }

    public User(String name, String surname, String birthday, String electronic, String password) {
        this();
        this.setSurname(surname);
        this.setName(name);
        this.setBirthday(birthday);
        this.setElectronic(electronic);
        this.setPassword(password);
    }

    public String getElectronic() {
        return electronic;
    }

    public void setElectronic(String electronic) {
        this.electronic = electronic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "Map [id="+id+", name=" + name + ", type=" + surname +", size="+ birthday +"]";
    }

}