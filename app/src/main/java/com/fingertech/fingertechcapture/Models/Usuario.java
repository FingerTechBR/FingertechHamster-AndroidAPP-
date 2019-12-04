package com.fingertech.fingertechcapture.Models;

public class Usuario {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String digital;
    private String digital_caminho;
    private String foto;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDigital_caminho() {
        return digital_caminho;
    }

    public void setDigital_caminho(String digital_caminho) {
        this.digital_caminho = digital_caminho;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDigital() {
        return digital;
    }

    public void setDigital(String digital) {
        this.digital = digital;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
