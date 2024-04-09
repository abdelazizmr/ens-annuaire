package com.ens.ensannuaire.controller;

import com.ens.ensannuaire.dao.DepartementDAO;
import com.ens.ensannuaire.dao.EtudiantDAO;
import com.ens.ensannuaire.dao.FiliereDAO;
import com.ens.ensannuaire.model.Departement;
import com.ens.ensannuaire.model.Filiere;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/filieres", "/admin-filieres", "/insertFiliere", "/editFiliere/*", "/deleteFiliere/*","/updateFiliere"})
public class FiliereController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("*********** Path get Filiere : " + path);

        if (path.startsWith("/deleteFiliere")) {
            deleteFiliere(request, response);
        } else if (path.startsWith("/insertFiliere")) {
            insertFiliere(request, response);
        } else if (path.startsWith("/editFiliere")) {
            editFiliere(request, response);
        } else {
            handleListFiliere(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("*********** Path post Filiere : " + path);

        if (path.startsWith("/insertFiliere")) {
            addFiliere(request, response);
        } else if (path.startsWith("/updateFiliere")) {
            updateFiliere(request, response);
        }
    }

    private void handleListFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String searchQuery = request.getParameter("searchQuery");
        List<Filiere> filieres;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Search etudiants based on the search query
            filieres = FiliereDAO.searchFiliereParNom(searchQuery);
        } else {
            // Retrieve all etudiants
            filieres = FiliereDAO.getAllFilieres();
        }

        request.setAttribute("filieres", filieres);

        // Determine if the user is an admin
        boolean isAdmin = path.startsWith("/admin");
        request.setAttribute("isAdmin", isAdmin);

        // Forward the request to the listeEtudiant.jsp view
        request.getRequestDispatcher("/views/filiere/listeFiliere.jsp").forward(request, response);
    }


    private void insertFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Departement> departements = DepartementDAO.getAllDepartements();
        request.setAttribute("departements", departements);
        request.getRequestDispatcher("/views/filiere/insertFiliere.jsp").forward(request, response);

    }

    private void addFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String nom = request.getParameter("nom");
            int departementId = Integer.parseInt(request.getParameter("departementId"));
            Filiere filiere= new Filiere(0,nom, departementId);

            // Add the etudiant to the database
            FiliereDAO.addFiliere(filiere);

            System.out.println("***** created successfully");

            // Set flash message and redirect
            request.getSession().setAttribute("flashMessage", "filiere created successfully");
            response.sendRedirect(request.getContextPath() + "/admin-filieres");
        } catch (Exception e) {
            // Set error flash message and redirect
            request.getSession().setAttribute("flashMessage", "Failed to create filiere");
            response.sendRedirect(request.getContextPath() + "/admin-filieres");
            System.out.println("############### Error adding filiere: " + e.getMessage());
        }
    }

    private void editFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = path.split("/");
        int cne = 0;


        if (parts.length == 3) {
            String cneString = parts[2];
            try {
                cne = Integer.parseInt(cneString);

                // Retrieve etudiant from database using CNE
                Filiere filiere = FiliereDAO.getFiliereById(cne);

                if (filiere != null) {

                    List<Departement> departements = DepartementDAO.getAllDepartements();

                    request.setAttribute("departements", departements);

                    request.setAttribute("filiere", filiere);
                    request.getRequestDispatcher("/views/filiere/editFiliere.jsp").forward(request, response);
                    return; // Return here to prevent redirecting at the end
                } else {
                    // Set error flash message and redirect
                    request.getSession().setAttribute("flashMessage", "Filiere not found");
                }
            } catch (NumberFormatException e) {
                // Set error flash message and redirect if CNE is not a valid number
                request.getSession().setAttribute("flashMessage", "Invalid CNE format");
            } catch (Exception e) {
                // Set error flash message and redirect for other exceptions
                request.getSession().setAttribute("flashMessage", "Failed to retrieve filiere");
                System.out.println("############### Error retrieving filiere: " + e.getMessage());
            }
        } else {
            // Set error flash message and redirect if URL format is invalid
            request.getSession().setAttribute("flashMessage", "Invalid URL format for edit filiere");
        }

        // Redirect to admin-etudiants page
        response.sendRedirect(request.getContextPath() + "/admin-filieres");
    }

    private void updateFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parse request parameters and create Etudiant object
            int id = Integer.parseInt(request.getParameter("id"));
            String nom = request.getParameter("nom");
            int departementId = Integer.parseInt(request.getParameter("departementId"));
            Filiere filiere = new Filiere(id,nom, departementId);

            // Update the etudiant in the database
            FiliereDAO.updateFiliere(filiere);

            // Set flash message and redirect
            request.getSession().setAttribute("flashMessage", "Filiere updated successfully");
            response.sendRedirect(request.getContextPath() + "/admin-filieres");
        } catch (Exception e) {
            // Set error flash message and redirect
            request.getSession().setAttribute("flashMessage", "Failed to update filiere");
            response.sendRedirect(request.getContextPath() + "/admin-filieres");
            System.out.println("############### Error updating filiere: " + e.getMessage());
        }
    }

    private void deleteFiliere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = path.split("/");
        int cne = 0;

        // Assuming the format is "/deleteEtudiant/{cne}"
        if (parts.length == 3) {
            String id = parts[2];
            try {
                cne = Integer.parseInt(id);
                FiliereDAO.deleteFiliere(cne);
                request.getSession().setAttribute("flashMessage", "Filiere deleted successfully");
            } catch (NumberFormatException e) {
                System.out.println("Invalid id format: " + id);
                request.getSession().setAttribute("flashMessage", "Failed to delete Filiere: Invalid CNE format");
            } catch (SQLException e) {
                System.out.println("Error deleting filiere: " + e.getMessage());
                request.getSession().setAttribute("flashMessage", "Failed to delete filiere: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid URL format for filiere: " + path);
            request.getSession().setAttribute("flashMessage", "Failed to delete filiere: Invalid URL format");
        }

        response.sendRedirect(request.getContextPath() + "/admin-filieres");
    }


}
