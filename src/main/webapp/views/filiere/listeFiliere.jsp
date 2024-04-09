<%@ page import="java.util.List" %>
<%@ page import="com.ens.ensannuaire.model.Filiere" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Filières</title>
</head>
<body>
<% if ((boolean)request.getAttribute("isAdmin")) { %>
<%@ include file="/views/admin/index.jsp" %>
<%}else{%>
<%@ include file="/views/user/index.jsp" %>
<%}%>

<%@ include file="/views/filiere/rechercherFiliere.html" %>
<h2 class="text-center">Liste des Filières</h2>
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
        List<Filiere> filieres = (List<Filiere>) request.getAttribute("filieres");
        if (filieres.isEmpty()) {
    %>
    <p class="text-center">Aucune filière trouvée.</p>
    <%
    } else {
    %>
    <% if ((boolean)request.getAttribute("isAdmin")) { %>
    <div class="d-flex justify-content-end my-3">
        <a class="btn btn-success" href="${pageContext.request.contextPath}/insertFiliere">Ajouter Filière</a>
    </div>
    <% } %>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Département</th>
                <% if ((boolean)request.getAttribute("isAdmin")) { %>
                <th>Actions</th>
                <% } %>
            </tr>
            </thead>
            <tbody>
            <% for (Filiere filiere : filieres) { %>
            <tr>
                <td><%= filiere.getId() %></td>
                <td><%= filiere.getNom() %></td>
                <td><%= filiere.getDepartementNom(filiere.getDepartementId()) %></td>
                <% if ((boolean)request.getAttribute("isAdmin")) { %>
                <td class="d-flex">
                    <a href="${pageContext.request.contextPath}/editFiliere/<%= filiere.getId() %>" class="btn btn-primary me-2">Edit</a>
                    <a href="${pageContext.request.contextPath}/deleteFiliere/<%= filiere.getId() %>" class="btn btn-danger">Delete</a>
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
