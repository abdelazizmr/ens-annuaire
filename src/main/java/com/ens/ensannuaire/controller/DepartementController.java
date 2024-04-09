package com.ens.ensannuaire.controller;

import com.ens.ensannuaire.dao.DepartementDAO;
import com.ens.ensannuaire.model.Departement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/departements", "/admin-departements", "/insertDepartement", "/editDepartement/*", "/deleteDepartement/*", "/updateDepartement"})
public class DepartementController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("*********** Path get Departement : " + path);

        if (path.startsWith("/deleteDepartement")) {
            deleteDepartement(request, response);
        } else if (path.startsWith("/insertDepartement")) {
            insertDepartement(request, response);
        } else if (path.startsWith("/editDepartement")) {
            editDepartement(request, response);
        } else {
            handleListDepartement(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("*********** Path post Departement : " + path);

        if (path.startsWith("/insertDepartement")) {
            addDepartement(request, response);
        } else if (path.startsWith("/updateDepartement")) {
            updateDepartement(request, response);
        }
    }

    private void handleListDepartement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String searchQuery = request.getParameter("searchQuery");
        List<Departement> departements;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Search departements based on the search query
            departements = DepartementDAO.searchDepartementByName(searchQuery);
        } else {
            // Retrieve all departements
            departements = DepartementDAO.getAllDepartements();
        }

        request.setAttribute("departements", departements);

        // Determine if the user is an admin
        boolean isAdmin = path.startsWith("/admin");
        request.setAttribute("isAdmin", isAdmin);

        // Forward the request to the listeDepartement.jsp view
        request.getRequestDispatcher("/views/departement/listeDepartement.jsp").forward(request, response);
    }

    private void insertDepartement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/departement/insertDepartement.jsp").forward(request, response);
    }

    private void addDepartement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nom = request.getParameter("nom");
            Departement departement = new Departement(0, nom);

            // Add the departement to the database
            DepartementDAO.addDepartement(departement);

            System.out.println("***** created successfully");

            // Set flash message and redirect
            request.getSession().setAttribute("flashMessage", "Departement created successfully");
            response.sendRedirect(request.getContextPath() + "/admin-departements");
        } catch (Exception e) {
            // Set error flash message and redirect
            request.getSession().setAttribute("flashMessage", "Failed to create departement");
            response.sendRedirect(request.getContextPath() + "/admin-departements");
            System.out.println("############### Error adding departement: " + e.getMessage());
        }
    }

    private void editDepartement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = path.split("/");
        int id = 0;

        if (parts.length == 3) {
            String idString = parts[2];
            try {
                id = Integer.parseInt(idString);

                // Retrieve departement from database using ID
                Departement departement = DepartementDAO.getDepartementById(id);

                if (departement != null) {
                    request.setAttribute("departement", departement);
                    request.getRequestDispatcher("/views/departement/editDepartement.jsp").forward(request, response);
                    return; // Return here to prevent redirecting at the end
                } else {
                    // Set error flash message and redirect
                    request.getSession().setAttribute("flashMessage", "Departement not found");
                }
            } catch (NumberFormatException e) {
                // Set error flash message and redirect if ID is not a valid number
                request.getSession().setAttribute("flashMessage", "Invalid ID format");
            } catch (Exception e) {
                // Set error flash message and redirect for other exceptions
                request.getSession().setAttribute("flashMessage", "Failed to retrieve departement");
                System.out.println("############### Error retrieving departement: " + e.getMessage());
            }
        } else {
            // Set error flash message and redirect if URL format is invalid
            request.getSession().setAttribute("flashMessage", "Invalid URL format for edit departement");
        }

        // Redirect to admin-departements page
        response.sendRedirect(request.getContextPath() + "/admin-departements");
    }

    private void updateDepartement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parse request parameters and create Departement object
            int id = Integer.parseInt(request.getParameter("id"));
            String nom = request.getParameter("nom");
            Departement departement = new Departement(id, nom);

            // Update the departement in the database
            DepartementDAO.updateDepartement(departement);

            // Set flash message and redirect
            request.getSession().setAttribute("flashMessage", "Departement updated successfully");
            response.sendRedirect(request.getContextPath() + "/admin-departements");
        } catch (Exception e) {
            // Set error flash message and redirect
            request.getSession().setAttribute("flashMessage", "Failed to update departement");
            response.sendRedirect(request.getContextPath() + "/admin-departements");
            System.out.println("############### Error updating departement: " + e.getMessage());
        }
    }

    private void deleteDepartement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = path.split("/");
        int id = 0;

        if (parts.length == 3) {
            String idString = parts[2];
            try {
                id = Integer.parseInt(idString);
                DepartementDAO.deleteDepartement(id);
                request.getSession().setAttribute("flashMessage", "Departement deleted successfully");
            } catch (NumberFormatException e) {
                System.out.println("Invalid id format: " + idString);
                request.getSession().setAttribute("flashMessage", "Failed to delete Departement: Invalid ID format");
            } catch (SQLException e) {
                System.out.println("Error deleting departement: " + e.getMessage());
                request.getSession().setAttribute("flashMessage", "Failed to delete departement: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid URL format for departement: " + path);
            request.getSession().setAttribute("flashMessage", "Failed to delete departement: Invalid URL format");
        }

        response.sendRedirect(request.getContextPath() + "/admin-departements");
    }
}
