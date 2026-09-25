package cl.EducarParaTodos.dao;

import cl.EducarParaTodos.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UsuarioDAO {

    private EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    // ==========================================
    // 1. CONSULTA DE LECTURA
    // ==========================================
    public List<Usuario> listarTodos() {
        String jpql = "SELECT u FROM Usuario u";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        return query.getResultList();
    }

    // ==========================================
    // 2. OPERACIÓN DELETE MASIVA (Requisito Rúbrica)
    // ==========================================
    /*
     * Elimina a todos los usuarios cuyo email termine en un dominio específico.
     * Ejemplo: eliminarUsuariosPorDominio("@spam.com");
     */
    public int eliminarUsuariosPorDominio(String dominio) {
        em.getTransaction().begin();
        
        String jpql = "DELETE FROM Usuario u WHERE u.email LIKE :dominio";
        Query query = em.createQuery(jpql);
        query.setParameter("dominio", "%" + dominio);
        
        int filasEliminadas = query.executeUpdate();
        
        em.getTransaction().commit();
        return filasEliminadas;
    }
}