package com.nictas.eclipselink.postgresql.repos;
import com.nictas.eclipselink.postgresql.domain.ClientJPA;
import com.nictas.eclipselink.postgresql.domain.DepotJPA;
import com.nictas.eclipselink.postgresql.utils.EntityManagerFactorySingleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepotRepository {

    private EntityManager entityManager;

    private static final DepotRepository instance = new DepotRepository();

    private DepotRepository() {
    }

    public static DepotRepository getInstance() {
        return instance;
    }

    public void create(DepotJPA depot) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(depot);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public DepotJPA findById(Integer id) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        DepotJPA depot = entityManager.find(DepotJPA.class, id);
        entityManager.close();
        return depot;
    }

    public DepotJPA findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<DepotJPA> query = entityManager.createNamedQuery("DepotJPA.findByName", DepotJPA.class);
        query.setParameter("name", name);
        DepotJPA depot = query.getSingleResult();
        entityManager.close();
        return depot;
    }

    public List<DepotJPA> findAll() {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<DepotJPA> query = entityManager.createNamedQuery("DepotJPA.findAll", DepotJPA.class);
        List<DepotJPA> depots = query.getResultList();
        entityManager.close();
        return depots;
    }

    /*public List<Author> findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Author> query = entityManager.createNamedQuery("Author.findByName", Author.class);
        query.setParameter("name",  name);
        List<Author> authors = query.getResultList();
        entityManager.close();
        return authors;
    }*/



}