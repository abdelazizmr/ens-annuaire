<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter Département</title>
</head>
<body>
<%@ include file="/views/admin/index.jsp" %>
<h2 class="text-center">Ajouter Departement</h2>
<div class="container">
    <form action="${pageContext.request.contextPath}/insertDepartement" method="post">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom du departement:</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>
        <button type="submit" class="btn btn-primary">Ajouter</button>
    </form>
</div>
</body>
</html>
