package com.developer.edu.app_arco.model;

public class Etapa {

    String id;
    String nome_etapa;
    String descricao_etapa_lider;
    String descricao_etapa_menbro;
    String nome_tematica;
    String descricao_tematica;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome_etapa() {
        return nome_etapa;
    }

    public void setNome_etapa(String nome_etapa) {
        this.nome_etapa = nome_etapa;
    }

    public String getDescricao_etapa_lider() {
        return descricao_etapa_lider;
    }

    public void setDescricao_etapa_lider(String descricao_etapa_lider) {
        this.descricao_etapa_lider = descricao_etapa_lider;
    }

    public String getDescricao_etapa_menbro() {
        return descricao_etapa_menbro;
    }

    public void setDescricao_etapa_menbro(String descricao_etapa_menbro) {
        this.descricao_etapa_menbro = descricao_etapa_menbro;
    }

    public String getNome_tematica() {
        return nome_tematica;
    }

    public void setNome_tematica(String nome_tematica) {
        this.nome_tematica = nome_tematica;
    }

    public String getDescricao_tematica() {
        return descricao_tematica;
    }

    public void setDescricao_tematica(String descricao_tematica) {
        this.descricao_tematica = descricao_tematica;
    }

    public Etapa(String id, String nome_etapa, String descricao_etapa_lider, String descricao_etapa_menbro, String nome_tematica, String descricao_tematica) {
        this.id = id;
        this.nome_etapa = nome_etapa;
        this.descricao_etapa_lider = descricao_etapa_lider;
        this.descricao_etapa_menbro = descricao_etapa_menbro;
        this.nome_tematica = nome_tematica;
        this.descricao_tematica = descricao_tematica;
    }
}
