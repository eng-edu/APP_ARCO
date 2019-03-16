package com.developer.edu.app_arco.model;

public class Opiniao {

    String ID;
    String ID_ETAPA;
    String NOME_ETAPA;
    String ID_USUARIO;
    String DATA_HORA;
    String TEXTO;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_ETAPA() {
        return ID_ETAPA;
    }

    public void setID_ETAPA(String ID_ETAPA) {
        this.ID_ETAPA = ID_ETAPA;
    }

    public String getNOME_ETAPA() {
        return NOME_ETAPA;
    }

    public void setNOME_ETAPA(String NOME_ETAPA) {
        this.NOME_ETAPA = NOME_ETAPA;
    }

    public String getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(String ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
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

    public Opiniao(String ID, String ID_ETAPA, String NOME_ETAPA, String ID_USUARIO, String DATA_HORA, String TEXTO) {
        this.ID = ID;
        this.ID_ETAPA = ID_ETAPA;
        this.NOME_ETAPA = NOME_ETAPA;
        this.ID_USUARIO = ID_USUARIO;
        this.DATA_HORA = DATA_HORA;
        this.TEXTO = TEXTO;
    }
}
