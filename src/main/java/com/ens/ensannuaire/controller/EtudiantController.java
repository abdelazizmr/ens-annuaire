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
import java.util.List;


@WebServlet({"/etudiants","/admin-etudiants","/insertEtudiant","/editEtudiant/*","/deleteEtudiant/*"})
public class EtudiantController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("*********** Path Etudiant : "+path);
        String searchQuery = request.getParameter("searchQuery");

        if(path.startsWith("/deleteEtudiant")){
            String[] parts = path.split("/");
            int cne = 0;
            if (parts.length == 3) { // Assuming the format is "/deleteEtudiant/{id}"
                String idString = parts[2];
                try {
                    cne = Integer.parseInt(idString);
                    System.out.println("cne : " + cne);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID format: " + idString);
                }
            } else {
                System.out.println("Invalid URL format for deleteEtudiant: " + path);
            }
            try {
                EtudiantDAO.deleteEtudiant(cne);
                request.getSession().setAttribute("flashMessage", "Student deleted successfully");
                response.sendRedirect(request.getContextPath() + "/admin-etudiants");
                return;
            }catch (Exception e){
                request.getSession().setAttribute("flashMessage", "Failed to delete student");
                response.sendRedirect(request.getContextPath() + "/admin-etudiants");
                System.out.println("############### Error deleting student: " + e.getMessage());
            }
        }

        if(path.startsWith("/insertEtudiant")){
            List<Departement> departements = DepartementDAO.getAllDepartements();
            List<Filiere> filieres = FiliereDAO.getAllFilieres();
            request.setAttribute("departements",departements);
            request.setAttribute("filieres",filieres);
            request.getRequestDispatcher("/views/etudiant/insertEtudiant.jsp").forward(request, response);
            return;
        }

        List<model.Etudiant> etudiants = null;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            etudiants = EtudiantDAO.searchEtudiant(searchQuery);
        } else {
            etudiants = EtudiantDAO.getAllEtudiants();
        }

        request.setAttribute("etudiants", etudiants);



        if(path.startsWith("/admin")){
            request.setAttribute("isAdmin",true);
            request.getRequestDispatcher("/views/etudiant/listeEtudiant.jsp").forward(request, response);
        }else{
            request.setAttribute("isAdmin",false);
            request.getRequestDispatcher("/views/etudiant/listeEtudiant.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try{
            int cne = Integer.parseInt(request.getParameter("cne"));
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String telephone = String.valueOf(request.getParameter("telephone"));
            int filiereId = Integer.parseInt(request.getParameter("filiereId"));
            int departementId = Integer.parseInt(request.getParameter("departementId"));

            model.Etudiant etudiant = new model.Etudiant(cne, nom, prenom, telephone, filiereId, departementId);


            EtudiantDAO.addEtudiant(etudiant);
            request.getSession().setAttribute("flashMessage", "Student created successfully");
            response.sendRedirect(request.getContextPath() + "/admin-etudiants");
        }catch (Exception e){
            request.getSession().setAttribute("flashMessage", "Failed to create student");
            response.sendRedirect(request.getContextPath() + "/admin-etudiants");
            System.out.println("############### Error adding student: " + e.getMessage());
        }

    }


}
