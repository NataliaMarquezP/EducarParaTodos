package cl.EducarParaTodos.dao;

import cl.EducarParaTodos.modelo.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CursoDAO {

    private EntityManager em;

    // El controlador le pasará el EntityManager (la conexión) a este DAO
    public CursoDAO(EntityManager em) {
        this.em = em;
    }

    // ==========================================
    // 1. CONSULTAS DE BÚSQUEDA (SELECT JPQL)
    // ==========================================

    public List<Curso> buscarPorTema(String tema) {
        // JPQL: Usamos la entidad Curso y su atributo "tema"
        String jpql = "SELECT c FROM Curso c WHERE c.tema = :tema";
        TypedQuery<Curso> query = em.createQuery(jpql, Curso.class);
        query.setParameter("tema", tema);
        return query.getResultList();
    }

    public List<Curso> buscarPorNivel(String nivel) {
        String jpql = "SELECT c FROM Curso c WHERE c.nivel = :nivel";
        TypedQuery<Curso> query = em.createQuery(jpql, Curso.class);
        query.setParameter("nivel", nivel);
        return query.getResultList();
    }

    public List<Curso> buscarPorPopularidadMinima(int popularidadMinima) {
        String jpql = "SELECT c FROM Curso c WHERE c.popularidad >= :popMin";
        TypedQuery<Curso> query = em.createQuery(jpql, Curso.class);
        query.setParameter("popMin", popularidadMinima);
        return query.getResultList();
    }

    // ==========================================
    // 2. OPERACIONES MASIVAS (UPDATE y DELETE)
    // ==========================================

    /* 
     * UPDATE MASIVO: Suma puntos de popularidad a todos los cursos de un tema específico.
     * Retorna la cantidad de registros que fueron actualizados.
     */
    public int incrementarPopularidadPorTema(String tema, int puntosExtra) {
        em.getTransaction().begin();
        
        String jpql = "UPDATE Curso c SET c.popularidad = c.popularidad + :puntos WHERE c.tema = :tema";
        Query query = em.createQuery(jpql);
        query.setParameter("puntos", puntosExtra);
        query.setParameter("tema", tema);
        
        // executeUpdate() es la clave aquí, se usa para UPDATE y DELETE
        int filasActualizadas = query.executeUpdate(); 
        
        em.getTransaction().commit();
        return filasActualizadas;
    }

    /* 
     * DELETE MASIVO: Elimina todos los cursos que tengan una popularidad 
     * menor al número indicado.
     */
    public int eliminarCursosPocoPopulares(int popularidadMinima) {
        em.getTransaction().begin();
        
        String jpql = "DELETE FROM Curso c WHERE c.popularidad < :min";
        Query query = em.createQuery(jpql);
        query.setParameter("min", popularidadMinima);
        
        int filasEliminadas = query.executeUpdate();
        
        em.getTransaction().commit();
        return filasEliminadas;
    }
}