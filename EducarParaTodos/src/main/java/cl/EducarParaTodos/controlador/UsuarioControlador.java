package cl.EducarParaTodos.controlador;

import cl.EducarParaTodos.dao.UsuarioDAO;
import cl.EducarParaTodos.modelo.Usuario;
import cl.EducarParaTodos.utilidades.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        EntityManager em = JPAUtil.getEntityManager();
        UsuarioDAO usuarioDAO = new UsuarioDAO(em);

        try {
            switch (accion) {
                case "listar":
                    // Obtiene todos los usuarios y los envía a la vista
                    List<Usuario> lista = usuarioDAO.listarTodos();
                    request.setAttribute("usuarios", lista);
                    request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                    break;
                    
                case "eliminar":
                    // Elimina un usuario por su ID
                    Long id = Long.parseLong(request.getParameter("id"));
                    em.getTransaction().begin();
                    Usuario u = em.find(Usuario.class, id);
                    if (u != null) {
                        em.remove(u);
                    }
                    em.getTransaction().commit();
                    response.sendRedirect("usuarios?accion=listar");
                    break;
                    
                default:
                    response.sendRedirect("usuarios?accion=listar");
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

        try {
            switch (accion) {
                case "crear":
                    // Captura los datos del formulario JSP y los guarda en la BD
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setNombre(request.getParameter("nombre"));
                    nuevoUsuario.setEmail(request.getParameter("email"));

                    em.getTransaction().begin();
                    em.persist(nuevoUsuario);
                    em.getTransaction().commit();
                    
                    response.sendRedirect("usuarios?accion=listar");
                    break;
            }
        } finally {
            em.close();
        }
    }
}