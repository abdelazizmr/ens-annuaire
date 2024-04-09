<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Filière</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/views/admin/index.jsp" %>
<div class="container my-3">
    <h2 class="text-center mb-3">Edit Filière</h2>
    <form id="editFiliereForm" action="${pageContext.request.contextPath}/updateFiliere" method="post">
        <div class="form-group">
            <label for="filiereId">ID:</label>
            <input type="text" id="filiereId" name="id" class="form-control" value="${filiere.id}" readonly>
        </div>

        <div class="form-group">
            <label for="nom">Nom:</label>
            <input type="text" id="nom" name="nom" class="form-control" value="${filiere.nom}" required>
        </div>

        <div class="form-group">
            <label for="departementId">Departement:</label>
            <select id="departementId" name="departementId" class="form-control">
                <c:forEach var="departement" items="${departements}">
                    <option value="${departement.id}" <c:if test="${departement.id eq filiere.departementId}">selected</c:if>>${departement.nom}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Update Filiere</button>
    </form>
</div>

<script>
    // Function to validate string input
    function validateStringInput() {
        let nom = document.getElementById('nom').value.trim();

        // Check if the nom input is empty
        if (nom === '') {
            alert('Nom cannot be empty.');
            return false;
        }

        return true;
    }

    // Attach validation function to form submission
    document.getElementById('editFiliereForm').onsubmit = function() {
        return validateStringInput();
    };
</script>
</body>
</html>
