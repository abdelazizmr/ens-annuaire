package com.ens.ensannuaire.model;

public class Departement {
    private int id;
    private String nom;

    // Constructor

    public Departement(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }


    // Getter and setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
