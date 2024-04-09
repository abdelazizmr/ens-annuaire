<%@ page import="java.util.List" %>
<%@ page import="com.ens.ensannuaire.model.Departement" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Départements</title>
</head>
<body>
<% if ((boolean)request.getAttribute("isAdmin")) { %>
<%@ include file="/views/admin/index.jsp" %>
<%}else{%>
<%@ include file="/views/user/index.jsp" %>
<%}%>

<%@ include file="/views/departement/rechercherDepartement.html" %>
<h2 class="text-center">Liste des Départements</h2>
<div class="container">
    <%-- Flash message --%>
    <%
        String flashMessage = (String) request.getSession().getAttribute("flashMessage");
        if (flashMessage != null) {
    %>
    <div class="alert alert-primary" role="alert">
        <%= flashMessage %>
    </div>
    <%
        // Clear the flash message after displaying it
        request.getSession().removeAttribute("flashMessage");
    %>
    <%
        }
    %>

    <%
        List<Departement> departements = (List<Departement>) request.getAttribute("departements");
        if (departements.isEmpty()) {
    %>
    <p class="text-center">Aucun département trouvé.</p>
    <%
    } else {
    %>
    <% if ((boolean)request.getAttribute("isAdmin")) { %>
    <div class="d-flex justify-content-end my-3">
        <a class="btn btn-success" href="${pageContext.request.contextPath}/insertDepartement">Ajouter Département</a>
    </div>
    <% } %>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <% if ((boolean)request.getAttribute("isAdmin")) { %>
                <th>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (Departement departement : departements) { %>
            <tr>
                <td><%= departement.getId() %></td>
                <td><%= departement.getNom() %></td>
                <% if ((boolean)request.getAttribute("isAdmin")) { %>
                <td class="d-flex">
                    <a href="${pageContext.request.contextPath}/editDepartement/<%= departement.getId() %>" class="btn btn-primary me-2">Edit</a>
                    <a href="${pageContext.request.contextPath}/deleteDepartement/<%= departement.getId() %>" class="btn btn-danger">Delete</a>
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
