package com.ens.ensannuaire.controller;

import com.ens.ensannuaire.dao.EtudiantDAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/etudiants")
public class EtudiantController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String searchQuery = request.getParameter("searchQuery");
        List<model.Etudiant> etudiants = null;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            etudiants = EtudiantDAO.searchEtudiant(searchQuery);
        } else {
            etudiants = EtudiantDAO.getAllEtudiants();
        }

        request.setAttribute("etudiants", etudiants);

        request.getRequestDispatcher("/views/etudiant/listeEtudiant.jsp").forward(request, response);
    }


}
