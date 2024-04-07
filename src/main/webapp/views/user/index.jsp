<!-- File: index.jsp -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ENS Mini Directory - User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body class="bg-light">


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto d-flex justify-content-center gap-3">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/etudiants">Etudiants</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/filieres">Filieres</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/departements">Departments</a>
            </li>

        </ul>
    </div>
</nav>

</body>
</html>
