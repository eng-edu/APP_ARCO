package com.developer.edu.app_arco.model;

public class Comentario {

    String ID_AUTOR;
    String ID_LIDER;
    String DATA_HORA;
    String TEXTO;

    public Comentario(String ID_AUTOR, String ID_LIDER, String DATA_HORA, String TEXTO) {
        this.ID_AUTOR = ID_AUTOR;
        this.ID_LIDER = ID_LIDER;
        this.DATA_HORA = DATA_HORA;
        this.TEXTO = TEXTO;
    }

    public String getID_AUTOR() {
        return ID_AUTOR;
    }

    public void setID_AUTOR(String ID_AUTOR) {
        this.ID_AUTOR = ID_AUTOR;
    }

    public String getID_LIDER() {
        return ID_LIDER;
    }

    public void setID_LIDER(String ID_LIDER) {
        this.ID_LIDER = ID_LIDER;
    }

    public String getDATA_HORA() {
        return DATA_HORA;
    }

    public void setDATA_HORA(String DATA_HORA) {
        this.DATA_HORA = DATA_HORA;
    }

    public String getTEXTO() {
        return TEXTO;
    }

    public void setTEXTO(String TEXTO) {
        this.TEXTO = TEXTO;
    }
}
