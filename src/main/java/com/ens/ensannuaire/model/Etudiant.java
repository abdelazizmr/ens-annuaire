package model;

import com.ens.ensannuaire.dao.DepartementDAO;
import com.ens.ensannuaire.dao.FiliereDAO;

public class Etudiant {
    private int cne;
    private String nom;
    private String prenom;
    private String telephone;
    private int filiereId;
    private int departementId;
    private String departementNom;
    private String filiereNom;

    // Constructor
    public Etudiant(int cne, String nom, String prenom, String telephone, int filiereId, int departementId) {
        this.cne = cne;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.filiereId = filiereId;
        this.departementId = departementId;
    }

    public int getCne() {
        return cne;
    }

    public void setCne(int cne) {
        this.cne = cne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getFiliereId() {
        return filiereId;
    }

    public void setFiliereId(int filiereId) {
        this.filiereId = filiereId;
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

    public String getFiliereNom(int id) {
        return FiliereDAO.getFiliereNameById(id);
    }

    public void setFiliereNom(String filiereNom) {
        this.filiereNom = filiereNom;
    }
}
