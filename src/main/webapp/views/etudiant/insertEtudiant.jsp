<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Student</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/views/admin/index.jsp" %>
<div class="container my-3">
    <h2 class="text-center mb-3">Add Student</h2>
    <form id="addStudentForm" action="${pageContext.request.contextPath}/etudiants" method="post">
        <div class="form-group">
            <label for="cne">CNE:</label>
            <input type="text" id="cne" name="cne" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="nom">Nom:</label>
            <input type="text" id="nom" name="nom" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="prenom">Prenom:</label>
            <input type="text" id="prenom" name="prenom" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="telephone">Telephone:</label>
            <input type="text" id="telephone" name="telephone" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="filiereId">Filiere:</label>
            <select id="filiereId" name="filiereId" class="form-control">
                <c:forEach var="filiere" items="${filieres}">
                    <option value="${filiere.id}">${filiere.nom}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="departementId">Departement:</label>
            <select id="departementId" name="departementId" class="form-control">
                <c:forEach var="departement" items="${departements}">
                    <option value="${departement.id}">${departement.nom}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Add Student</button>
    </form>
</div>

<script>
    // Function to validate string inputs
    function validateStringInputs() {
        let nom = document.getElementById('nom').value;
        let prenom = document.getElementById('prenom').value;

        // Check if string inputs are empty
        if (nom.trim() === '' || prenom.trim() === '') {
            alert('Nom and Prénom cannot be empty.');
            return false;
        }

        // Check if nom and prenom contains numbers
        if(/\d/.test(nom) || /\d/.test(prenom)) {
            alert('Nom and Prénom should not contain numbers.');
            return false;
        }

        return true;
    }

    // Function to validate numeric inputs
    function validateNumericInputs() {
        let cne = document.getElementById('cne').value;
        let telephone = document.getElementById('telephone').value;

        // Check if cne and telephone are numeric
        if (isNaN(cne) || isNaN(telephone)) {
            alert('CNE, Telephone must be numeric.');
            return false;
        }
        return true;
    }

    function validateSelectes(){
        let departement = document.getElementById("departementId").value;
        let filiere = document.getElementById("filiereId").value;

        if(filiere === "" || departement === ""){
            alert("You must enter the value of filiere and departement")
            return false;
        }

        return true;


    }

    // Attach validation functions to form submission
    document.getElementById('addStudentForm').onsubmit = function() {
        return validateNumericInputs() && validateStringInputs() && validateSelectes();
    };

</script>
</body>
</html>
