<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle del Curso</title>
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
        
        <div class="card mb-4 shadow-sm">
        <!-- inicio panel alumnos -->
        <div class="card mb-4 shadow-sm border-info">
            <div class="card-header bg-info text-white d-flex justify-content-between align-items-center">
                <h4 class="m-0"><i class="bi bi-people-fill me-2"></i>Alumnos Inscritos</h4>
                <!-- Botón de para eliminar a todos los usuariops -->
                <a href="cursos?accion=vaciarAlumnos&idCurso=${curso.id}" 
                   class="btn btn-danger btn-sm fw-bold shadow" 
                   onclick="return confirm('ATENCIÓN: ¿Seguro que deseas eliminar masivamente a TODOS los alumnos de este curso?');">
                   <i class="bi bi-trash-fill"></i> Vaciar Curso
                </a>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Nombre del Alumno</th>
                                <th>Correo Electrónico</th>
                                <th class="text-end">Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- JSTL recorre la lista -->
                            <c:forEach var="estudiante" items="${curso.estudiantes}">
                                <tr>
                                    <td class="align-middle">${estudiante.nombre}</td>
                                    <td class="align-middle">${estudiante.email}</td>
                                    <td class="text-end">
                                        <!-- Botón para eliminar a 1 elemento -->
                                        <a href="cursos?accion=eliminarAlumno&idCurso=${curso.id}&idUsuario=${estudiante.id}" 
                                           class="btn btn-outline-danger btn-sm">
                                           Desinscribir
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            
                            <!-- si está vacía mostramos un mensaje personalizado -->
                            <c:if test="${empty curso.estudiantes}">
                                <tr>
                                    <td colspan="3" class="text-center text-muted p-4">
                                        <i class="bi bi-info-circle fs-4 d-block mb-2"></i>
                                        Aún no hay alumnos inscritos en este curso.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- fin panel alumnos -->
            <div class="card-body">
                <h2 class="card-title">${curso.titulo}</h2>
                <span class="badge bg-secondary mb-2">Nivel: ${curso.nivel}</span>
                <span class="badge bg-primary mb-2">Tema: ${curso.tema}</span>
                
                <hr>
                <form action="cursos" method="POST" class="row g-3 align-items-center">
                    <input type="hidden" name="accion" value="inscribir">
                    <input type="hidden" name="idCurso" value="${curso.id}">
                    
                    <div class="col-auto">
                        <label class="col-form-label"><b>Inscribir Alumno:</b></label>
                    </div>
                    <div class="col-auto">
                        <select name="idUsuario" class="form-select" required>
                            <option value="">Selecciona un usuario...</option>
                            <c:forEach var="u" items="${usuarios}">
                                <option value="${u.id}">${u.nombre} (${u.email})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-success">Inscribir</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3>Lecciones del curso</h3>
            <a href="formulario_leccion.jsp?idCurso=${curso.id}" class="btn btn-primary">Agregar Lección</a>
        </div>

        <div class="accordion" id="acordeonLecciones">
            <c:forEach var="leccion" items="${curso.lecciones}" varStatus="status">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="heading${status.index}">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${status.index}">
                            Lección ${status.index + 1}: ${leccion.titulo}
                        </button>
                    </h2>
                    <div id="collapse${status.index}" class="accordion-collapse collapse" data-bs-parent="#acordeonLecciones">
                        <div class="accordion-body">
                            ${leccion.contenido}
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty curso.lecciones}">
                <p class="text-muted">Aún no hay lecciones en este curso.</p>
            </c:if>
        </div>
        
        <br>
        <a href="cursos?accion=listar" class="btn btn-secondary">Volver al listado</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>