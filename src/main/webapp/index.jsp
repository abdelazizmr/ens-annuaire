<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ens - Annuaire</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            padding: 20px;
        }
        h1 {
            margin-bottom: 20px;
        }
        ul {
            list-style: none;
            padding: 0;
        }
        li {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center">Welcome to Annuaire de l'ENS</h1>
    <h2 class="text-center">Choose an interface:</h2>
    <ul class="text-center">
        <li><a href="${pageContext.request.contextPath}/admin" class="btn btn-primary my-3">Administration</a></li>
        <li><a href="${pageContext.request.contextPath}/user" class="btn btn-secondary">Utilisateur</a></li>
    </ul>
</div>
</body>
</html>
