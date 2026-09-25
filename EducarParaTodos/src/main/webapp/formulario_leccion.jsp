<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar Lección</title>
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
    <div class="container mt-5">
        <h2>Agregar Nueva Lección</h2>
        
        <form action="lecciones" method="POST" class="mt-4">
            <input type="hidden" name="accion" value="crear">
            <input type="hidden" name="idCurso" value="${param.idCurso}">
            
            <div class="mb-3">
                <label for="titulo" class="form-label">Título de la Lección</label>
                <input type="text" class="form-control" id="titulo" name="titulo" required>
            </div>
            
            <div class="mb-3">
                <label for="contenido" class="form-label">Contenido</label>
                <textarea class="form-control" id="contenido" name="contenido" rows="5" required></textarea>
            </div>
            
            <button type="submit" class="btn btn-primary">Guardar Lección</button>
            <a href="cursos?accion=verDetalle&id=${param.idCurso}" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>