package com.ens.ensannuaire.dao;

import com.ens.ensannuaire.util.ConnectionDB;
import model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private static PreparedStatement st;

    // Method to retrieve all étudiants from the database
    public static List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();

        try {
            // Get database connection
            connection = ConnectionDB.getConnection();

            // Prepare SQL statement
            String sql = "SELECT * FROM Etudiant";
            statement = connection.createStatement();

            // Execute query
            resultSet = statement.executeQuery(sql);

            // Process results
            while (resultSet.next()) {
                int cne = resultSet.getInt("cne");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                int filiere = resultSet.getInt("filiereId");
                int departement = resultSet.getInt("departementId");


                // Create and add Etudiant object to the list
                Etudiant etudiant = new Etudiant(cne, nom, prenom,telephone, filiere, departement);
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while fetching list of students : "+e.getMessage());
        }

        return etudiants;
    }

    public static List<Etudiant> searchEtudiant(String searchQuery){
        List<Etudiant> searchResults = new ArrayList<>();
        try {

            Connection connection = ConnectionDB.getConnection();
            String sql = "SELECT * FROM Etudiant WHERE nom LIKE ? OR cne LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(2, "%" + searchQuery + "%");
            ResultSet resultSet = statement.executeQuery();

            // Process the results
            while (resultSet.next()) {
                int cne = resultSet.getInt("cne");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                int filiereId = resultSet.getInt("filiereId");
                int departementId = resultSet.getInt("departementId");
                Etudiant etudiant = new Etudiant(cne, nom, prenom, telephone, filiereId, departementId);
                searchResults.add(etudiant);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error searching on student : "+e.getMessage());
        }

        return searchResults;
    }

    public static void addEtudiant(Etudiant etudiant) throws SQLException {

            connection = ConnectionDB.getConnection();

            // Prepare SQL statement
            String sql = "INSERT INTO Etudiant (cne, nom, prenom, telephone, filiereId, departementId) VALUES (?, ?, ?, ?, ?, ?)";
            st = connection.prepareStatement(sql);

            // Set parameters
            st.setInt(1, etudiant.getCne());
            st.setString(2, etudiant.getNom());
            st.setString(3, etudiant.getPrenom());
            st.setString(4, etudiant.getTelephone());
            st.setInt(5, etudiant.getFiliereId());
            st.setInt(6, etudiant.getDepartementId());

            // Execute the statement
            st.executeUpdate();

    }

    public static void deleteEtudiant(int cne) throws SQLException {

            // Get database connection
            connection = ConnectionDB.getConnection();

            // Prepare SQL statement
            String sql = "DELETE FROM Etudiant WHERE cne = ?";
            st = connection.prepareStatement(sql);

            // Set parameter values
            st.setInt(1, cne);

            // Execute the delete statement
            st.executeUpdate();

    }

    public static Etudiant getEtudiantByCne(int id) throws SQLException {

        Etudiant etudiant = null;

        try {
            connection = ConnectionDB.getConnection();
            String query = "SELECT * FROM etudiant WHERE cne = ?";
            st = connection.prepareStatement(query);
            st.setInt(1, id);
            resultSet = st.executeQuery();

            if (resultSet.next()) {
                // Retrieve etudiant details from ResultSet
                int cne = resultSet.getInt("cne");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                int filiereId = resultSet.getInt("filiereId");
                int departementId = resultSet.getInt("departementId");

                // Create the etudiant object
                etudiant = new Etudiant(cne, nom, prenom, telephone, filiereId, departementId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return etudiant;
    }




    public static void updateEtudiant(Etudiant etudiant) throws SQLException {
        try {
            connection = ConnectionDB.getConnection();
            String query = "UPDATE etudiant SET nom = ?, prenom = ?, telephone = ?, filiereId = ?, departementId = ? WHERE cne = ?";
            st = connection.prepareStatement(query);
            st.setString(1, etudiant.getNom());
            st.setString(2, etudiant.getPrenom());
            st.setString(3, etudiant.getTelephone());
            st.setInt(4, etudiant.getFiliereId());
            st.setInt(5, etudiant.getDepartementId());
            st.setInt(6, etudiant.getCne());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating : "+e.getMessage());;
        }
    }




}
