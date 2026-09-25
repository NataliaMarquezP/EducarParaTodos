package cl.EducarParaTodos.utilidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    
    // Al ser estático, este bloque pesado se ejecuta solo una vez cuando la app arranca
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducarParaTodosPU");

    // Este es el método que usarán para DAOs o Controladores
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    // para cuando se bote el servidor Tomcat
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}