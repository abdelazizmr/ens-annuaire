<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Étudiants</title>
</head>
<body>
<%@ include file="/views/user/index.jsp" %>

<%@ include file="/views/etudiant/rechercherEtudiant.html" %>
<h2 class="text-center">Liste des Étudiants</h2>
<div class="container">
    <%
        List<model.Etudiant> etudiants = (List<model.Etudiant>) request.getAttribute("etudiants");
        if (etudiants.isEmpty()) {
    %>
    <p class="text-center">Aucun étudiant trouvé.</p>
    <%
    } else {
    %>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>CNE</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Téléphone</th>
                <th>Filière</th>
                <th>Département</th>
            </tr>
            </thead>
            <tbody>
            <% for (model.Etudiant etudiant : etudiants) { %>
            <tr>
                <td><%= etudiant.getCne() %></td>
                <td><%= etudiant.getNom() %></td>
                <td><%= etudiant.getPrenom() %></td>
                <td><%= etudiant.getTelephone() %></td>
                <td><%= etudiant.getFiliereNom(etudiant.getFiliereId()) %></td>
                <td><%= etudiant.getDepartementNom(etudiant.getDepartementId()) %></td>
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
