# EducaParaTodos - Plataforma Educativa en Línea 🎓

Este proyecto es una plataforma educativa web diseñada para ofrecer cursos gratuitos a comunidades desfavorecidas. Permite la gestión completa de estudiantes, cursos y lecciones, garantizando una integración eficiente a nivel de base de datos a través de Mapeo Objeto-Relacional (ORM) y consultas JPQL avanzadas.

Proyecto desarrollado bajo la estructura metodológica **J2EE (Jakarta EE)** sobre un modelo de arquitectura **MVC (Modelo-Vista-Controlador)**.

---

## 🛠️ Tecnologías y Arquitectura

*   **Lenguaje:** Java (JDK 11+)
*   **Servidor de Aplicaciones:** Apache Tomcat (v10)
*   **Persistencia (ORM):** Jakarta Persistence API (JPA) implementado con Hibernate (v5.6+)
*   **Base de Datos:** MySQL 8
*   **Gestor de Dependencias:** Maven
*   **Frontend:** HTML5, JSP, JSTL, CSS3 puro y Bootstrap 5 (CDN)
*   **Arquitectura:** Patrón MVC (Modelos/Entidades, Controladores/Servlets, DAOs/Repositorios, Vistas/JSP)

---

## ⚙️ Características Técnicas Implementadas (Resultados de Aprendizaje)

Este proyecto cumple con los requerimientos académicos (RA3 y RA4) demostrando el uso avanzado de ventajas de programación orientada a objetos en bases de datos:

1.  **Mapeo Objeto-Relacional (ORM):**
    *   Implementación de entidades `@Entity` (`Usuario`, `Curso`, `Leccion`).
    *   Relación `Uno a Muchos` (`@OneToMany` / `@ManyToOne`) entre Cursos y Lecciones.
    *   Relación `Muchos a Muchos` (`@ManyToMany`) para gestionar la inscripción de Usuarios en Cursos.
2.  **Operaciones CRUD y Controladores:**
    *   Servlets independientes (`CursoControlador`, `UsuarioControlador`, `LeccionControlador`) que manejan peticiones HTTP (GET/POST) y orquestan la comunicación entre la vista y la capa DAO.
3.  **Consultas JPQL Avanzadas:**
    *   Extracción de datos orientada a objetos (`SELECT c FROM Curso c WHERE...`).
    *   Solución al problema de *Lazy Loading* mediante la cláusula `JOIN FETCH` para obtener cursos y lecciones en una sola transacción.
4.  **Operaciones Masivas (UPDATE / DELETE):**
    *   Manejo de colecciones y transacciones `.executeUpdate()` para eliminar múltiples usuarios por dominio o actualizar popularidad masivamente sin saturar la memoria RAM.
5.  **Diseño Interactivo y Responsivo:**
    *   Vistas diseñadas con **Bootstrap 5**, asegurando adaptabilidad a dispositivos móviles (Mobile-First).
    *   Implementación de componentes dinámicos (Acordeones, Tarjetas, Formularios interactivos) e inyección de datos mediante **JSTL**.

---

## 🚀 Instalación y Despliegue

Para ejecutar este proyecto en un entorno local:

1.  **Base de Datos:** 
    *   Abrir MySQL/phpMyAdmin y crear una base de datos vacía llamada `educaparatodos_db`.
    *   *Nota: No es necesario ejecutar scripts SQL (CREATE TABLE), JPA construirá el esquema automáticamente al iniciar.*
2.  **Configuración del IDE (Eclipse):**
    *   Importar el proyecto como **Maven Project**.
    *   Actualizar las dependencias (`Alt + F5` -> Update Project).
3.  **Configuración de Conexión:**
    *   Verificar que las credenciales de MySQL (usuario/contraseña) coincidan en el archivo `src/main/resources/META-INF/persistence.xml`.
4.  **Despliegue:**
    *   Añadir el proyecto a un servidor **Apache Tomcat** previamente configurado en el IDE.
    *   Iniciar el servidor y acceder a `http://localhost:8080/EducarParaTodos`.

---

## 🗺️ Roadmap (Trabajo Futuro)

Debido a los plazos de entrega, el proyecto se centró en asegurar una base arquitectónica robusta (JPA/MVC). Quedan pendientes las siguientes funcionalidades para la versión 2.0:

- [x] **Panel de Administración:** Creado (Dashboard principal y gestión CRUD).
- [ ] **Sistema de Autenticación (Login):** Creación de pantalla de inicio de sesión validada contra la base de datos usando encriptación de contraseñas (ej. BCrypt).
- [ ] **Perfiles y Roles de Usuario:** Separación de privilegios mediante roles (`ADMINISTRADOR`, `ESTUDIANTE`, `PROFESOR`), restringiendo el acceso a los Servlets mediante filtros (Filtros Servlet J2EE).
- [ ] **Métricas y Reportes:** Implementación de consultas JPQL de agregación (`COUNT`, `AVG`, `GROUP BY`) para visualizar estadísticas en tiempo real (ej. cursos más populares, tasa de inscripción).
- [ ] **Gestor de Contenido Multimedia:** Capacidad para subir imágenes y videos a las lecciones.
- [ ] **Evaluaciones y Certificados:** Módulo para crear quizzes por lección y emitir un certificado al completar un curso.
