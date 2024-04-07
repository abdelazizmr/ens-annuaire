<%@ page import="java.util.List" %>
<%@ page import="com.ens.ensannuaire.model.Filiere" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Filieres</title>
</head>
<body>
<%@ include file="/views/user/index.jsp" %>

<%@ include file="/views/filiere/rechercherFiliere.html" %>
<h2 class="text-center">Liste des Filieres</h2>
<div class="container">
    <%
        List<Filiere> filieres = (List<Filiere>) request.getAttribute("filieres");
        if (filieres.isEmpty()) {
    %>
    <p class="text-center">Aucune filière trouvée.</p>
    <%
    } else {
    %>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Département</th>
            </tr>
            </thead>
            <tbody>
            <% for (Filiere filiere : filieres) { %>
            <tr>
                <td><%= filiere.getId() %></td>
                <td><%= filiere.getNom() %></td>
                <td><%= filiere.getDepartementNom(filiere.getDepartementId()) %></td>
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
