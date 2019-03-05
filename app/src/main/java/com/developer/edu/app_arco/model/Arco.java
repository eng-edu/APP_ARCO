package com.developer.edu.app_arco.model;

public class Arco {
    String ID;
    String SITUACAO;
    String ID_LIDER;
    String NOME_EQUIPE;
    String NOME_TEMATICA;
    String DESCRICAO_TEMATICA;
    String DATA_HORA;

    public Arco(String ID, String SITUACAO, String ID_LIDER, String NOME_GRUPO, String NOME_TEMATICA, String DESCRICAO_TEMATICA, String DATA_HORA) {
        this.ID = ID;
        this.SITUACAO = SITUACAO;
        this.ID_LIDER = ID_LIDER;
        this.NOME_EQUIPE = NOME_GRUPO;
        this.NOME_TEMATICA = NOME_TEMATICA;
        this.DESCRICAO_TEMATICA = DESCRICAO_TEMATICA;
        this.DATA_HORA = DATA_HORA;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSITUACAO() {
        return SITUACAO;
    }

    public void setSITUACAO(String SITUACAO) {
        this.SITUACAO = SITUACAO;
    }

    public String getID_LIDER() {
        return ID_LIDER;
    }

    public void setID_LIDER(String ID_LIDER) {
        this.ID_LIDER = ID_LIDER;
    }

    public String getNOME_EQUIPE() {
        return NOME_EQUIPE;
    }

    public void setNOME_EQUIPE(String NOME_EQUIPE) {
        this.NOME_EQUIPE = NOME_EQUIPE;
    }

    public String getNOME_TEMATICA() {
        return NOME_TEMATICA;
    }

    public void setNOME_TEMATICA(String NOME_TEMATICA) {
        this.NOME_TEMATICA = NOME_TEMATICA;
    }

    public String getDESCRICAO_TEMATICA() {
        return DESCRICAO_TEMATICA;
    }

    public void setDESCRICAO_TEMATICA(String DESCRICAO_TEMATICA) {
        this.DESCRICAO_TEMATICA = DESCRICAO_TEMATICA;
    }

    public String getDATA_HORA() {
        return DATA_HORA;
    }

    public void setDATA_HORA(String DATA_HORA) {
        this.DATA_HORA = DATA_HORA;
    }
}
