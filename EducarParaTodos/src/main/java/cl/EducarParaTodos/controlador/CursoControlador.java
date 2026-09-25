package cl.EducarParaTodos.controlador;

import cl.EducarParaTodos.dao.CursoDAO;
import cl.EducarParaTodos.modelo.Curso;
import cl.EducarParaTodos.modelo.Usuario;
import cl.EducarParaTodos.utilidades.JPAUtil; // Tu clase que genera la conexión

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Esta anotación define la URL a la que responderá este controlador
@WebServlet("/cursos")
public class CursoControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        EntityManager em = JPAUtil.getEntityManager();
        CursoDAO cursoDAO = new CursoDAO(em);

        try {
        	switch (accion) {
            case "listar":
                List<Curso> lista = cursoDAO.buscarPorNivel("Básico"); // O buscar todos
                request.setAttribute("cursos", lista);
                request.getRequestDispatcher("buscar_cursos.jsp").forward(request, response);
                break;
                
            case "verDetalle":
                Long idCurso = Long.parseLong(request.getParameter("id"));
                
                String jpql = "SELECT c FROM Curso c LEFT JOIN FETCH c.lecciones WHERE c.id = :id";
                Curso c = em.createQuery(jpql, Curso.class)
                            .setParameter("id", idCurso)
                            .getSingleResult();
                
                // TRUCO EXPERTO DE JPA: Como la conexión se cierra al final del Servlet,
                // llamamos a .size() para forzar a Hibernate a traer los estudiantes a la memoria RAM 
                // antes de que se cierre el EntityManager. Evita el "LazyInitializationException".
                c.getEstudiantes().size(); 
                
                List<Usuario> listaUsuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
                
                request.setAttribute("curso", c);
                request.setAttribute("usuarios", listaUsuarios);
                request.getRequestDispatcher("detalle_curso.jsp").forward(request, response);
                break;
                
            case "eliminarAlumno":
                // Quita a 1 solo alumno del curso
                Long idCur = Long.parseLong(request.getParameter("idCurso"));
                Long idUsu = Long.parseLong(request.getParameter("idUsuario"));
                
                em.getTransaction().begin();
                Usuario usuario = em.find(Usuario.class, idUsu);
                Curso cursoObj = em.find(Curso.class, idCur);
                
                if (usuario != null && cursoObj != null) {
                    // Como Usuario manda en la relación, le quitamos el curso a él
                    usuario.getCursosInscritos().remove(cursoObj);
                    em.merge(usuario);
                }
                em.getTransaction().commit();
                
                response.sendRedirect("cursos?accion=verDetalle&id=" + idCur);
                break;

            case "vaciarAlumnos":
                // OPERACIÓN MASIVA: Quita a todos los alumnos del curso
                Long idCursoVaciar = Long.parseLong(request.getParameter("idCurso"));
                
                em.getTransaction().begin();
                Curso cursoVaciar = em.find(Curso.class, idCursoVaciar);
                
                if (cursoVaciar != null) {
                    // Recorremos todos los alumnos inscritos y los desvinculamos
                    for (Usuario u : cursoVaciar.getEstudiantes()) {
                        u.getCursosInscritos().remove(cursoVaciar);
                        em.merge(u); // Actualizamos cada registro masivamente
                    }
                }
                em.getTransaction().commit();
                
                response.sendRedirect("cursos?accion=verDetalle&id=" + idCursoVaciar);
                break;
                
            case "eliminar":
                Long id = Long.parseLong(request.getParameter("id"));
                em.getTransaction().begin();
                Curso curDel = em.find(Curso.class, id);
                if (curDel != null) em.remove(curDel);
                em.getTransaction().commit();
                response.sendRedirect("cursos?accion=listar");
                break;
                
            default:
                response.sendRedirect("cursos?accion=listar");
        }
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        EntityManager em = JPAUtil.getEntityManager();
        CursoDAO cursoDAO = new CursoDAO(em);

        try {
            switch (accion) {
                case "crear":
                    // Crear un registro nuevo (CREATE)
                    Curso nuevoCurso = new Curso();
                    nuevoCurso.setTitulo(request.getParameter("titulo"));
                    nuevoCurso.setTema(request.getParameter("tema"));
                    nuevoCurso.setNivel(request.getParameter("nivel"));
                    nuevoCurso.setPopularidad(0);

                    em.getTransaction().begin();
                    em.persist(nuevoCurso);
                    em.getTransaction().commit();
                    
                    response.sendRedirect("cursos?accion=listar");
                    break;

                case "actualizar":
                    // Actualizar un registro existente (UPDATE)
                    Long id = Long.parseLong(request.getParameter("id"));
                    em.getTransaction().begin();
                    Curso cursoEdit = em.find(Curso.class, id);
                    cursoEdit.setTitulo(request.getParameter("titulo"));
                    // ... setear demás campos
                    em.getTransaction().commit();
                    
                    response.sendRedirect("cursos?accion=listar");
                    break;

                // ==========================================
                // OPERACIONES MASIVAS (Requisito de la tarea)
                // ==========================================
                
                case "actualizarMasivo":
                    String temaTarget = request.getParameter("tema");
                    int puntos = Integer.parseInt(request.getParameter("puntos"));
                    
                    cursoDAO.incrementarPopularidadPorTema(temaTarget, puntos);
                    response.sendRedirect("cursos?accion=listar");
                    break;

                case "eliminarMasivo":
                    int popMinima = Integer.parseInt(request.getParameter("popularidadMinima"));
                    
                    cursoDAO.eliminarCursosPocoPopulares(popMinima);
                    response.sendRedirect("cursos?accion=listar");
                    break;
                    
                case "inscribir":
                    Long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
                    Long idCursoInsc = Long.parseLong(request.getParameter("idCurso"));

                    em.getTransaction().begin();
                    Usuario u = em.find(Usuario.class, idUsuario);
                    Curso cur = em.find(Curso.class, idCursoInsc);

                    if (u != null && cur != null) {
                        // JPA hace la magia aquí: al agregarlo a la lista, hace el INSERT en la tabla 'usuario_curso'
                        u.getCursosInscritos().add(cur);
                        em.merge(u); 
                    }
                    em.getTransaction().commit();
                    
                    // Recarga la página del curso
                    response.sendRedirect("cursos?accion=verDetalle&id=" + idCursoInsc);
                    break;
            }
        } finally {
            em.close();
        }
    }
}