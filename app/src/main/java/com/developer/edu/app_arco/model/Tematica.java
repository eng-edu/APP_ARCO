package com.developer.edu.app_arco.model;

public class Tematica {

    String id;
    String nome;
    String descricao;
    String idade_minima;

    public Tematica(String id, String nome, String descricao, String idade_minima) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idade_minima = idade_minima;
    }

    @Override
    public String toString() {
        return "Tematica{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idade_minima='" + idade_minima + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdade_minima() {
        return idade_minima;
    }

    public void setIdade_minima(String idade_minima) {
        this.idade_minima = idade_minima;
    }
}
