package com.michel.first_spring_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nacionalidades")
public class Nacionalidade {

    @Id
    @Column(name = "iso2", length = 2)
    private String iso2;

    @Column(name = "pais", nullable = false, length = 100)
    private String pais;

    @Column(name = "gentilico_masc", nullable = false, length = 60)
    private String gentilicoMasc;

    @Column(name = "gentilico_fem", nullable = false, length = 60)
    private String gentilicoFem;

    public Nacionalidade() {}

    public Nacionalidade(String iso2, String pais, String gentilicoMasc, String gentilicoFem) {
        this.iso2 = iso2;
        this.pais = pais;
        this.gentilicoMasc = gentilicoMasc;
        this.gentilicoFem = gentilicoFem;
    }

    // Getters e Setters
    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGentilicoMasc() {
        return gentilicoMasc;
    }

    public void setGentilicoMasc(String gentilicoMasc) {
        this.gentilicoMasc = gentilicoMasc;
    }

    public String getGentilicoFem() {
        return gentilicoFem;
    }

    public void setGentilicoFem(String gentilicoFem) {
        this.gentilicoFem = gentilicoFem;
    }

    @Override
    public String toString() {
        return pais + " (" + gentilicoMasc + ")";
    }
}
