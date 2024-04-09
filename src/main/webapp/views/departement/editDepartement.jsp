<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier Département</title>
</head>
<body>
<%@ include file="/views/admin/index.jsp" %>
<h2 class="text-center">Modifier Departement</h2>
<div class="container">
    <form action="${pageContext.request.contextPath}/updateDepartement" method="post">
        <input type="hidden" name="id" value="${departement.id}">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom du departement:</label>
            <input type="text" class="form-control" id="nom" name="nom" value="${departement.nom}" required>
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
    </form>
</div>
</body>
</html>
