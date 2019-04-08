package com.developer.edu.app_arco.model;

public class Ranking {

    String ID_USUARIO;
    String CURTIDAS;
    String ESTRELAS;


    public Ranking(String ID_USUARIO, String CURTIDAS, String ESTRELAS) {
        this.ID_USUARIO = ID_USUARIO;
        this.CURTIDAS = CURTIDAS;
        this.ESTRELAS = ESTRELAS;
    }


    public String getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(String ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public String getCURTIDAS() {
        return CURTIDAS;
    }

    public void setCURTIDAS(String CURTIDAS) {
        this.CURTIDAS = CURTIDAS;
    }

    public String getESTRELAS() {
        return ESTRELAS;
    }

    public void setESTRELAS(String ESTRELAS) {
        this.ESTRELAS = ESTRELAS;
    }

}
