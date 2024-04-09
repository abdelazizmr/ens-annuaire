package com.ens.ensannuaire.dao;

import com.ens.ensannuaire.model.Departement;
import com.ens.ensannuaire.util.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartementDAO {
    static Connection connection = null;
    static PreparedStatement statement = null;
    static ResultSet resultSet = null;



    public static List<Departement> getAllDepartements() {
        List<Departement> departements = new ArrayList<>();
        try {
            connection = ConnectionDB.getConnection();
            String sql = "SELECT * FROM Departement";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                Departement departement = new Departement(id, nom);
                departements.add(departement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while fetching list of departments: " + e.getMessage());
        }

        return departements;
    }


    public static String getDepartementNameById(int departementId) {
        String departementName = null;
        try {
            connection = ConnectionDB.getConnection();
            String sql = "SELECT nom FROM Departement WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, departementId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                departementName = resultSet.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while fetching department name: " + e.getMessage());
        }

        return departementName;
    }

    public static List<Departement> searchDepartementByName(String searchQuery) {
        List<Departement> searchResults = new ArrayList<>();
        try {
            connection = ConnectionDB.getConnection();
            String sql = "SELECT * FROM Departement WHERE nom LIKE ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchQuery + "%");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                Departement departement = new Departement(id, nom);
                searchResults.add(departement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while searching for departments by name: " + e.getMessage());
        }
        return searchResults;
    }

    public static Departement getDepartementById(int id) throws SQLException {
        Departement departement = null;
        try {
            connection = ConnectionDB.getConnection();
            String sql = "SELECT * FROM departement WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                departement = new Departement(id, nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while fetching departement by ID: " + e.getMessage());
        } finally {
            closeResources();
        }
        return departement;
    }

    public static void addDepartement(Departement departement) throws SQLException {
        try {
            connection = ConnectionDB.getConnection();
            String query = "INSERT INTO departement (nom) VALUES (?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, departement.getNom());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while adding departement: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    public static void updateDepartement(Departement departement) throws SQLException {
        try {
            connection = ConnectionDB.getConnection();
            String query = "UPDATE departement SET nom = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, departement.getNom());
            statement.setInt(2, departement.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while updating departement: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    public static void deleteDepartement(int id) throws SQLException {
        try {
            connection = ConnectionDB.getConnection();
            String query = "DELETE FROM departement WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting departement: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private static void closeResources() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while closing resources: " + e.getMessage());
        }
    }




}
