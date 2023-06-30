package com.example.ordemservico.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Cliente implements Serializable {
    private String id;
    private String nome;
    private String tipo;
    private String cpf;
    private String cep;
    private String endereco;

    public Cliente(String id, String nome, String tipo, String cpf, String cep, String endereco) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        String formatted = String.format("%s", this.getNome());
        return formatted;
    }
}