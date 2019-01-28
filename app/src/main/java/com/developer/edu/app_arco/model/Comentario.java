package com.developer.edu.app_arco.model;

public class Comentario {

    String id;
    String email;
    String tetxo;
    String data;
    String hora;

    public Comentario(String id, String email, String tetxo, String data, String hora) {
        this.id = id;
        this.email = email;
        this.tetxo = tetxo;
        this.data = data;
        this.hora = hora;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTetxo() {
        return this.tetxo;
    }

    public void setTetxo(String tetxo) {
        this.tetxo = tetxo;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
