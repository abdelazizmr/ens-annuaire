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


@WebServlet({"/etudiants","/admin-etudiants","/insertEtudiant","/editEtudiant/*","/deleteEtudiant/*","/updateEtudiant"})
public class EtudiantController extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("*********** Path get Etudiant : " + path);

        if (path.startsWith("/deleteEtudiant")) {
            deleteEtudiant(request, response);
        } else if (path.startsWith("/insertEtudiant")) {
            insertEtudiant(request, response);
        }else if(path.startsWith("/editEtudiant")){
            editEtudiant(request,response);
        }else {
            handleListEtudiant(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("*********** Path post Etudiant : " + path);

        if (path.startsWith("/insertEtudiant")) {
            addEtudiant(request, response);
        } else if (path.startsWith("/updateEtudiant")) {
            updateEtudiant(request, response);
        }
    }

    private void handleListEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String searchQuery = request.getParameter("searchQuery");
        List<model.Etudiant> etudiants;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            etudiants = EtudiantDAO.searchEtudiant(searchQuery);
        } else {
            etudiants = EtudiantDAO.getAllEtudiants();
        }
        request.setAttribute("etudiants", etudiants);

        if (path.startsWith("/admin")) {
            request.setAttribute("isAdmin", true);
        } else {
            request.setAttribute("isAdmin", false);
        }
        request.getRequestDispatcher("/views/etudiant/listeEtudiant.jsp").forward(request, response);
    }

    private void insertEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Departement> departements = DepartementDAO.getAllDepartements();
        List<Filiere> filieres = FiliereDAO.getAllFilieres();
        request.setAttribute("departements", departements);
        request.setAttribute("filieres", filieres);
        request.getRequestDispatcher("/views/etudiant/insertEtudiant.jsp").forward(request, response);
    }

    private void addEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parse request parameters and create Etudiant object
            int cne = Integer.parseInt(request.getParameter("cne"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String telephone = request.getParameter("telephone");
            int filiereId = Integer.parseInt(request.getParameter("filiereId"));
            int departementId = Integer.parseInt(request.getParameter("departementId"));
            model.Etudiant etudiant = new model.Etudiant(cne, nom, prenom, telephone, filiereId, departementId);

            // Add the etudiant to the database
            EtudiantDAO.addEtudiant(etudiant);

            System.out.println("***** created successfully");

            // Set flash message and redirect
            request.getSession().setAttribute("flashMessage", "Student created successfully");
            response.sendRedirect(request.getContextPath() + "/admin-etudiants");
        } catch (Exception e) {
            // Set error flash message and redirect
            request.getSession().setAttribute("flashMessage", "Failed to create student");
            response.sendRedirect(request.getContextPath() + "/admin-etudiants");
            System.out.println("############### Error adding student: " + e.getMessage());
        }
    }


    private void editEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = path.split("/");
        int cne = 0;


        if (parts.length == 3) {
            String cneString = parts[2];
            try {
                cne = Integer.parseInt(cneString);

                // Retrieve etudiant from database using CNE
                model.Etudiant etudiant = EtudiantDAO.getEtudiantByCne(cne);

                if (etudiant != null) {

                    List<Departement> departements = DepartementDAO.getAllDepartements();
                    List<Filiere> filieres = FiliereDAO.getAllFilieres();
                    request.setAttribute("departements", departements);
                    request.setAttribute("filieres", filieres);

                    request.setAttribute("etudiant", etudiant);
                    request.getRequestDispatcher("/views/etudiant/editEtudiant.jsp").forward(request, response);
                    return; // Return here to prevent redirecting at the end
                } else {
                    // Set error flash message and redirect
                    request.getSession().setAttribute("flashMessage", "Student not found");
                }
            } catch (NumberFormatException e) {
                // Set error flash message and redirect if CNE is not a valid number
                request.getSession().setAttribute("flashMessage", "Invalid CNE format");
            } catch (Exception e) {
                // Set error flash message and redirect for other exceptions
                request.getSession().setAttribute("flashMessage", "Failed to retrieve student");
                System.out.println("############### Error retrieving student: " + e.getMessage());
            }
        } else {
            // Set error flash message and redirect if URL format is invalid
            request.getSession().setAttribute("flashMessage", "Invalid URL format for editEtudiant");
        }

        // Redirect to admin-etudiants page
        response.sendRedirect(request.getContextPath() + "/admin-etudiants");

    }


    private void updateEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parse request parameters and create Etudiant object
            int cne = Integer.parseInt(request.getParameter("cne"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String telephone = request.getParameter("telephone");
            int filiereId = Integer.parseInt(request.getParameter("filiereId"));
            int departementId = Integer.parseInt(request.getParameter("departementId"));
            model.Etudiant etudiant = new model.Etudiant(cne, nom, prenom, telephone, filiereId, departementId);

            // Update the etudiant in the database
            EtudiantDAO.updateEtudiant(etudiant);

            // Set flash message and redirect
            request.getSession().setAttribute("flashMessage", "Student updated successfully");
            response.sendRedirect(request.getContextPath() + "/admin-etudiants");
        } catch (Exception e) {
            // Set error flash message and redirect
            request.getSession().setAttribute("flashMessage", "Failed to update student");
            response.sendRedirect(request.getContextPath() + "/admin-etudiants");
            System.out.println("############### Error updating student: " + e.getMessage());
        }
    }

    private void deleteEtudiant(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = path.split("/");
        int cne = 0;

        // Assuming the format is "/deleteEtudiant/{cne}"
        if (parts.length == 3) {
            String cneString = parts[2];
            try {
                cne = Integer.parseInt(cneString);
                EtudiantDAO.deleteEtudiant(cne);
                request.getSession().setAttribute("flashMessage", "Student deleted successfully");
            } catch (NumberFormatException e) {
                System.out.println("Invalid CNE format: " + cneString);
                request.getSession().setAttribute("flashMessage", "Failed to delete student: Invalid CNE format");
            } catch (SQLException e) {
                System.out.println("Error deleting student: " + e.getMessage());
                request.getSession().setAttribute("flashMessage", "Failed to delete student: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid URL format for deleteEtudiant: " + path);
            request.getSession().setAttribute("flashMessage", "Failed to delete student: Invalid URL format");
        }

        response.sendRedirect(request.getContextPath() + "/admin-etudiants");
    }

}

