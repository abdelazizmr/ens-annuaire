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




}
