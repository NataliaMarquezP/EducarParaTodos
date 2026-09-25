<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración - EducaParaTodos</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <!-- CSS Personalizado -->
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <!-- Menú de Navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
        <div class="container">
            <a class="navbar-brand" href="index.jsp"><i class="bi bi-mortarboard-fill me-2"></i>EducaParaTodos</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="cursos?accion=listar">Gestión Cursos</a></li>
                    <li class="nav-item"><a class="nav-link" href="usuarios?accion=listar">Gestión Usuarios</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Banner admin -->
    <header class="admin-panel text-center">
        <div class="container">
            <h1 class="display-4">Panel de Administración</h1>
            <p class="lead mt-3">Gestiona la plataforma educativa, administra cursos y controla los usuarios inscritos.</p>
        </div>
    </header>

    <main class="container mb-5">
        <div class="row g-4">
            
            <!-- Cursos -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card text-center p-4">
                    <div class="card-body">
                        <i class="bi bi-journal-bookmark-fill card-icon"></i>
                        <h4 class="card-title fw-bold">Gestión de Cursos</h4>
                        <p class="card-text text-muted">Crea nuevos cursos, edita contenidos y administra las lecciones disponibles en la academia.</p>
                        <a href="cursos?accion=listar" class="btn btn-primary mt-3 w-100">Administrar Cursos</a>
                    </div>
                </div>
            </div>

            <!-- Usuarios -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card text-center p-4">
                    <div class="card-body">
                        <i class="bi bi-people-fill card-icon"></i>
                        <h4 class="card-title fw-bold">Comunidad</h4>
                        <p class="card-text text-muted">Revisa la lista de alumnos registrados, elimina cuentas inactivas y registra nuevos participantes.</p>
                        <a href="usuarios?accion=listar" class="btn btn-primary mt-3 w-100">Administrar Usuarios</a>
                    </div>
                </div>
            </div>

            <!-- Reportes (algún día) -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card text-center p-4">
                    <div class="card-body">
                        <i class="bi bi-graph-up-arrow card-icon"></i>
                        <h4 class="card-title fw-bold">Métricas y Reportes</h4>
                        <p class="card-text text-muted">Analiza la popularidad de los cursos y el avance de las comunidades desfavorecidas.</p>
                        <button class="btn btn-outline-secondary mt-3 w-100" disabled>Próximamente</button>
                    </div>
                </div>
            </div>

        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>