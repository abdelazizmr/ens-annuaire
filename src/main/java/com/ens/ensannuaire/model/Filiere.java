package com.ens.ensannuaire.model;

import com.ens.ensannuaire.dao.DepartementDAO;

public class Filiere {
    private int id;
    private String nom;
    private int departementId;

    private String departementNom;

    // Constructor

    public Filiere(int id, String nom, int departementId) {
        this.id = id;
        this.nom = nom;
        this.departementId = departementId;
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

    public int getDepartementId() {
        return departementId;
    }

    public void setDepartementId(int departementId) {
        this.departementId = departementId;
    }

    public String getDepartementNom(int id) {
        return DepartementDAO.getDepartementNameById(id);
    }

    public void setDepartementNom(String departementNom) {
        this.departementNom = departementNom;
    }
}
