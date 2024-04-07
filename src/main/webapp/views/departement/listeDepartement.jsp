<%@ page import="java.util.List" %>
<%@ page import="com.ens.ensannuaire.model.Departement" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Départements</title>
</head>
<body>
<%@ include file="/views/user/index.jsp" %>

<%@ include file="/views/departement/rechercherDepartement.html" %>
<h2 class="text-center">Liste des Départements</h2>
<div class="container">
    <%
        List<Departement> departements = (List<Departement>) request.getAttribute("departements");
        if (departements.isEmpty()) {
    %>
    <p class="text-center">Aucun département trouvé.</p>
    <%
    } else {
    %>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Nom</th>
            </tr>
            </thead>
            <tbody>
            <% for (Departement departement : departements) { %>
            <tr>
                <td><%= departement.getId() %></td>
                <td><%= departement.getNom() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
