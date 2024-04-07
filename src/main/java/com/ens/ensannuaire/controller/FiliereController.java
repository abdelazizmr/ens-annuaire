package com.ens.ensannuaire.controller;

import com.ens.ensannuaire.dao.EtudiantDAO;
import com.ens.ensannuaire.dao.FiliereDAO;
import com.ens.ensannuaire.model.Filiere;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/filieres")
public class FiliereController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchQuery = request.getParameter("searchQuery");
        List<Filiere> filieres = null;
        if(searchQuery != null && !searchQuery.isEmpty()){
            filieres = FiliereDAO.searchFiliereParNom(searchQuery);
        }else{
            filieres = FiliereDAO.getAllFilieres();
        }



        request.setAttribute("filieres", filieres);

        request.getRequestDispatcher("/views/filiere/listeFiliere.jsp").forward(request, response);
    }


}
