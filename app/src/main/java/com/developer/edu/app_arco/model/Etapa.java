package com.developer.edu.app_arco.model;

public class Etapa {

    String id;
    String nome_etapa;
    String situacao_etapa;
    String codigo_etapa;
    String descricao_etapa;
    String nome_tematica;
    String descricao_tematica;

    @Override
    public String toString() {
        return "Etapa{" +
                "id='" + id + '\'' +
                ", nome_etapa='" + nome_etapa + '\'' +
                ", situacao_etapa='" + situacao_etapa + '\'' +
                ", codigo_etapa='" + codigo_etapa + '\'' +
                ", descricao_etapa='" + descricao_etapa + '\'' +
                ", nome_tematica='" + nome_tematica + '\'' +
                ", descricao_tematica='" + descricao_tematica + '\'' +
                '}';
    }

    public Etapa(String id, String nome_etapa, String situacao_etapa, String codigo_etapa, String descricao_etapa, String nome_tematica, String descricao_tematica) {
        this.id = id;
        this.nome_etapa = nome_etapa;
        this.situacao_etapa = situacao_etapa;
        this.codigo_etapa = codigo_etapa;
        this.descricao_etapa = descricao_etapa;
        this.nome_tematica = nome_tematica;
        this.descricao_tematica = descricao_tematica;
    }

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

    public String getSituacao_etapa() {
        return situacao_etapa;
    }

    public void setSituacao_etapa(String situacao_etapa) {
        this.situacao_etapa = situacao_etapa;
    }

    public String getCodigo_etapa() {
        return codigo_etapa;
    }

    public void setCodigo_etapa(String codigo_etapa) {
        this.codigo_etapa = codigo_etapa;
    }

    public String getDescricao_etapa() {
        return descricao_etapa;
    }

    public void setDescricao_etapa(String descricao_etapa) {
        this.descricao_etapa = descricao_etapa;
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
}
