package com.ens.ensannuaire.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ens.ensannuaire.model.Filiere;
import com.ens.ensannuaire.util.ConnectionDB;

public class FiliereDAO {
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement statement;

    private static Statement st;


    public static List<Filiere> getAllFilieres() {
        List<Filiere> filieres = new ArrayList<>();

        try {
            // Get database connection
            connection = ConnectionDB.getConnection();

            // Prepare SQL statement
            String sql = "SELECT * FROM Filiere";
            st = connection.createStatement();

            // Execute query
            resultSet = st.executeQuery(sql);

            // Process results
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                int departementId = resultSet.getInt("departementId");

                // Create and add Filiere object to the list
                Filiere filiere = new Filiere(id, nom, departementId);
                filieres.add(filiere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while fetching list of filieres: " + e.getMessage());
        }

        return filieres;
    }

    public static String getFiliereNameById(int filiereId) {
        String filiereName = null;
        try {
            connection = ConnectionDB.getConnection();
            String sql = "SELECT nom FROM Filiere WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, filiereId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                filiereName = resultSet.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while fetching filiere name: " + e.getMessage());
        }

        return filiereName;
    }

    public static List<Filiere> searchFiliereParNom(String searchQuery) {
        List<Filiere> searchResults = new ArrayList<>();
        try {
            connection = ConnectionDB.getConnection();
            String sql = "SELECT * FROM Filiere WHERE nom LIKE ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = statement.executeQuery();

            // Process the results
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                int departementId = resultSet.getInt("departementId");
                Filiere filiere = new Filiere(id, nom, departementId);
                searchResults.add(filiere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error searching for filieres by name: " + e.getMessage());
        }
        return searchResults;
    }

    public static void addFiliere(Filiere filiere) throws SQLException {


        try {
            connection = ConnectionDB.getConnection();
            String query = "INSERT INTO filiere (nom, departementId) VALUES (?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, filiere.getNom());
            statement.setInt(2, filiere.getDepartementId());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("############ Error adding filiere : "+e.getMessage());
        }
    }

    public static Filiere getFiliereById(int id) throws SQLException {

        Filiere filiere = null;

        try {
            connection = ConnectionDB.getConnection();
            String query = "SELECT * FROM filiere WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                filiere = new Filiere(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getInt("departementId")
                );
            }
        }catch (SQLException e){
            System.out.println("####### Error retriving filere : " +e.getMessage());
        }

        return filiere;
    }

    // Method to update a filière
    public static void updateFiliere(Filiere filiere) throws SQLException {

        try {
            connection = ConnectionDB.getConnection();
            String query = "UPDATE filiere SET nom = ?, departementId = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, filiere.getNom());
            statement.setInt(2, filiere.getDepartementId());
            statement.setInt(3, filiere.getId());
            statement.executeUpdate();
        }catch (SQLException e ){
            System.out.println("########## error updating filiere : "+e.getMessage());
        }
    }

    // Method to delete a filière by its ID
    public static void deleteFiliere(int id) throws SQLException {

        try {
            connection = ConnectionDB.getConnection();
            String query = "DELETE FROM filiere WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("######### error deleting filere : "+e.getMessage());
        }
    }

}
