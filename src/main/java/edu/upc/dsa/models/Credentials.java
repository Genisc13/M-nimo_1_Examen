package edu.upc.dsa.models;

public class Credentials {
    String correo;
    String password;

    public Credentials(){
        this.setCorreo("");
        this.setPassword("");
    }
    public Credentials(String correo, String password) {
        this.setCorreo(correo);
        this.setPassword(password);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
