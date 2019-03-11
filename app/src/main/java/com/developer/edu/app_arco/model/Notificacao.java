package com.developer.edu.app_arco.model;

public class Notificacao {

    String ID;
    String ID_ARCO;
    String ID_USUARIO;
    String DATA_HORA;
    String TEXTO;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_ARCO() {
        return ID_ARCO;
    }

    public void setID_ARCO(String ID_ARCO) {
        this.ID_ARCO = ID_ARCO;
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

    public Notificacao(String ID, String ID_ARCO, String ID_USUARIO, String DATA_HORA, String TEXTO) {
        this.ID = ID;
        this.ID_ARCO = ID_ARCO;
        this.ID_USUARIO = ID_USUARIO;
        this.DATA_HORA = DATA_HORA;
        this.TEXTO = TEXTO;
    }
}
