package cl.EducarParaTodos.controlador;

import cl.EducarParaTodos.modelo.Curso;
import cl.EducarParaTodos.modelo.Leccion;
import cl.EducarParaTodos.utilidades.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/lecciones")
public class LeccionControlador extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        EntityManager em = JPAUtil.getEntityManager();

        try {
            if ("crear".equals(accion)) {
                Long idCurso = Long.parseLong(request.getParameter("idCurso"));
                
                Leccion nuevaLeccion = new Leccion();
                nuevaLeccion.setTitulo(request.getParameter("titulo"));
                nuevaLeccion.setContenido(request.getParameter("contenido"));

                em.getTransaction().begin();
                // Buscamos el curso y se lo asignamos a la lección (Relación ManyToOne)
                Curso cursoAsociado = em.find(Curso.class, idCurso);
                nuevaLeccion.setCurso(cursoAsociado);
                
                em.persist(nuevaLeccion);
                em.getTransaction().commit();
                
                response.sendRedirect("cursos?accion=verDetalle&id=" + idCurso);
            }
        } finally {
            em.close();
        }
    }
}