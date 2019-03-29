package com.developer.edu.app_arco.model;

public class Comentario {

    String ID_AUTOR;
    String EMAIL_AUTOR;
    String ID_OPINIAO;
    String DATA_HORA;
    String TEXTO;

    public Comentario(String ID_AUTOR, String EMAIL_AUTOR, String ID_OPINIAO, String DATA_HORA, String TEXTO) {
        this.ID_AUTOR = ID_AUTOR;
        this.EMAIL_AUTOR = EMAIL_AUTOR;
        this.ID_OPINIAO = ID_OPINIAO;
        this.DATA_HORA = DATA_HORA;
        this.TEXTO = TEXTO;
    }

    public String getID_AUTOR() {
        return ID_AUTOR;
    }

    public void setID_AUTOR(String ID_AUTOR) {
        this.ID_AUTOR = ID_AUTOR;
    }

    public String getEMAIL_AUTOR() {
        return EMAIL_AUTOR;
    }

    public void setEMAIL_AUTOR(String EMAIL_AUTOR) {
        this.EMAIL_AUTOR = EMAIL_AUTOR;
    }

    public String getID_OPINIAO() {
        return ID_OPINIAO;
    }

    public void setID_OPINIAO(String ID_OPINIAO) {
        this.ID_OPINIAO = ID_OPINIAO;
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
