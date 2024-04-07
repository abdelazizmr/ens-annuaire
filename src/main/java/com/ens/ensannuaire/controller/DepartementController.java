package controller;

import com.ens.ensannuaire.dao.DepartementDAO;
import com.ens.ensannuaire.model.Departement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/departements")
public class DepartementController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchQuery = request.getParameter("searchQuery");

        List<Departement> departements = null;
        if(searchQuery != null && !searchQuery.isEmpty()){
            departements = DepartementDAO.searchDepartementByName(searchQuery);
        }else{
            departements = DepartementDAO.getAllDepartements();
        }

        request.setAttribute("departements", departements);

        request.getRequestDispatcher("/views/departement/listeDepartement.jsp").forward(request, response);
    }
}
