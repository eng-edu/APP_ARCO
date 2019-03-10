package com.developer.edu.app_arco.model;

public class Solicitacao {

    String ID;
    String NOME;
    String SOBRENOME;
    String DATA_NASC;
    String ESCOLARIDADE;

    public Solicitacao(String ID, String NOME, String SOBRENOME, String DATA_NASC, String ESCOLARIDADE) {
        this.ID = ID;
        this.NOME = NOME;
        this.SOBRENOME = SOBRENOME;
        this.DATA_NASC = DATA_NASC;
        this.ESCOLARIDADE = ESCOLARIDADE;
    }

    @Override
    public String toString() {
        return "Solicitacao{" +
                "ID='" + ID + '\'' +
                ", NOME='" + NOME + '\'' +
                ", SOBRENOME='" + SOBRENOME + '\'' +
                ", DATA_NASC='" + DATA_NASC + '\'' +
                ", ESCOLARIDADE='" + ESCOLARIDADE + '\'' +
                '}';
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getSOBRENOME() {
        return SOBRENOME;
    }

    public void setSOBRENOME(String SOBRENOME) {
        this.SOBRENOME = SOBRENOME;
    }

    public String getDATA_NASC() {
        return DATA_NASC;
    }

    public void setDATA_NASC(String DATA_NASC) {
        this.DATA_NASC = DATA_NASC;
    }

    public String getESCOLARIDADE() {
        return ESCOLARIDADE;
    }

    public void setESCOLARIDADE(String ESCOLARIDADE) {
        this.ESCOLARIDADE = ESCOLARIDADE;
    }
}
