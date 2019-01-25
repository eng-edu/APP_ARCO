package com.developer.edu.app_arco.model;

public class Etapa {

    String id;
    String codigo;
    String titulo;
    String id_arco;
    String texto;
    String ponto;
    String situacao;

    @Override
    public String toString() {
        return titulo;
    }

    public Etapa(String id, String codigo, String titulo, String id_arco, String texto, String ponto, String situacao) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.id_arco = id_arco;
        this.texto = texto;
        this.ponto = ponto;
        this.situacao = situacao;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId_arco() {
        return this.id_arco;
    }

    public void setId_arco(String id_arco) {
        this.id_arco = id_arco;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getPonto() {
        return this.ponto;
    }

    public void setPonto(String ponto) {
        this.ponto = ponto;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
