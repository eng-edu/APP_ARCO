package com.developer.edu.app_arco.model;

import android.widget.ScrollView;

public class Usuario {

    String id;
    String nome;
    String sobrenome;
    String escolaridade;
    String cpf;
    String data_nasc;
    String email;
    String sexo;
    String bio;
    String tipo;
    String situacao;
    String oline;

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", escolaridade='" + escolaridade + '\'' +
                ", cpf='" + cpf + '\'' +
                ", data_nasc='" + data_nasc + '\'' +
                ", email='" + email + '\'' +
                ", sexo='" + sexo + '\'' +
                ", bio='" + bio + '\'' +
                ", tipo='" + tipo + '\'' +
                ", situacao='" + situacao + '\'' +
                ", oline='" + oline + '\'' +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEscolaridade() {
        return this.escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getData_nasc() {
        return this.data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getOline() {
        return this.oline;
    }

    public void setOline(String oline) {
        this.oline = oline;
    }
}
