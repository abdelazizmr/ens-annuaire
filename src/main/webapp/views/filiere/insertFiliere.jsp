<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter Filière</title>
</head>
<body>
<%@ include file="/views/admin/index.jsp" %>

<div class="container my-3">
    <h2 class="text-center mb-3">Ajouter une nouvelle Filière</h2>
    <form id="addFiliereForm" action="${pageContext.request.contextPath}/insertFiliere" method="post">
        <div class="form-group">
            <label for="nom">Nom de la Filière:</label>
            <input type="text" id="nom" name="nom" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="departementId">Département:</label>
            <select id="departementId" name="departementId" class="form-control">
                <c:forEach var="departement" items="${departements}">
                    <option value="${departement.id}">${departement.nom}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Ajouter Filière</button>
    </form>
</div>

<script>
    // Function to validate string inputs
    function validateStringInputs() {
        let nom = document.getElementById('nom').value;

        // Check if string input is empty
        if (nom.trim() === '') {
            alert('Nom de la Filière ne peut pas être vide.');
            return false;
        }

        return true;
    }

    // Attach validation function to form submission
    document.getElementById('addFiliereForm').onsubmit = function() {
        return validateStringInputs();
    };
</script>
</body>
</html>
