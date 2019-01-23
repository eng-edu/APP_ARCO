package com.developer.edu.app_arco.model;

public class Arco {
    String ID;
    String TEMATICA;
    String TITULO;
    String PONTO;
    String GOSTEI;

    public Arco(String ID, String TEMATICA, String TITULO, String PONTO, String GOSTEI) {
        this.ID = ID;
        this.TEMATICA = TEMATICA;
        this.TITULO = TITULO;
        this.PONTO = PONTO;
        this.GOSTEI = GOSTEI;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTEMATICA() {
        return TEMATICA;
    }

    public void setTEMATICA(String TEMATICA) {
        this.TEMATICA = TEMATICA;
    }

    public String getTITULO() {
        return TITULO;
    }

    public void setTITULO(String TITULO) {
        this.TITULO = TITULO;
    }

    public String getPONTO() {
        return PONTO;
    }

    public void setPONTO(String PONTO) {
        this.PONTO = PONTO;
    }

    public String getGOSTEI() {
        return GOSTEI;
    }

    public void setGOSTEI(String GOSTEI) {
        this.GOSTEI = GOSTEI;
    }

    @Override
    public String toString() {
        return "Arco{" +
                "ID='" + ID + '\'' +
                ", TEMATICA='" + TEMATICA + '\'' +
                ", TITULO='" + TITULO + '\'' +
                ", PONTO='" + PONTO + '\'' +
                ", GOSTEI='" + GOSTEI + '\'' +
                '}';
    }
}
