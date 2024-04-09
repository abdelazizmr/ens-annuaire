<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Étudiants</title>
</head>
<body>
<% if ((boolean)request.getAttribute("isAdmin")) { %>
<%@ include file="/views/admin/index.jsp" %>
<%}else{%>
<%@ include file="/views/user/index.jsp" %>
<%}%>

<%@ include file="/views/etudiant/rechercherEtudiant.html" %>
<h2 class="text-center">Liste des Étudiants</h2>
<div class="container">

    <%-- flash message    --%>
        <%
            String flashMessage = (String) request.getSession().getAttribute("flashMessage");
            if (flashMessage != null) {
        %>
        <div id="flashMessage" class="alert alert-primary" role="alert">
            <%= flashMessage %>
        </div>
        <%
            // Clear the flash message after displaying it
            request.getSession().removeAttribute("flashMessage");
        %>
        <script>
            // Hide the flash message after 2 seconds
            setTimeout(function() {
                document.getElementById('flashMessage').style.display = 'none';
            }, 2000);
        </script>
        <% } %>



        <%
        List<model.Etudiant> etudiants = (List<model.Etudiant>) request.getAttribute("etudiants");
        if (etudiants.isEmpty()) {
    %>
    <p class="text-center">Aucun étudiant trouvé.</p>
    <%
    } else {
    %>
    <% if ((boolean)request.getAttribute("isAdmin")) { %>
    <div class="d-flex justify-content-end my-3">
        <a class="btn btn-success" href="${pageContext.request.contextPath}/insertEtudiant">Ajouter Étudiant</a>
    </div>
    <% } %>
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
                <% if ((boolean)request.getAttribute("isAdmin")) { %>
                <th>Actions</th>
                <% } %>
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
                <% if ((boolean)request.getAttribute("isAdmin")) { %>
                <td class="d-flex">
                    <a href="${pageContext.request.contextPath}/edit/<%= etudiant.getCne() %>" class="btn btn-primary me-2">Edit</a>
                    <a href="${pageContext.request.contextPath}/deleteEtudiant/<%= etudiant.getCne() %>" class="btn btn-danger">Delete</a>
                </td>
                <% } %>
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
