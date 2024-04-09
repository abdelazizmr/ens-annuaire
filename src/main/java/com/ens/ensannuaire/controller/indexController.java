package com.ens.ensannuaire.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet; // Import the WebServlet annotation

import java.io.IOException;

@WebServlet("/") // Map the servlet to the root URL
public class indexController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the appropriate JSP file based on the path
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.startsWith("/admin")) {
            request.getRequestDispatcher("/views/admin/index.jsp").forward(request, response);
        } else if (path.startsWith("/user")) {
            request.getRequestDispatcher("/views/user/index.jsp").forward(request, response);
        } else {
            // Handle invalid requests
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
