package com.nictas.eclipselink.postgresql.repos;
import com.nictas.eclipselink.postgresql.domain.ClientJPA;
import com.nictas.eclipselink.postgresql.domain.DepotJPA;
import com.nictas.eclipselink.postgresql.domain.VehicleJPA;
import com.nictas.eclipselink.postgresql.utils.EntityManagerFactorySingleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class VehicleRepository {
    private EntityManager entityManager;

    private static final VehicleRepository instance = new VehicleRepository();

    private VehicleRepository() {
    }

    public static VehicleRepository getInstance() {
        return instance;
    }

    public void create(VehicleJPA vehicle) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(vehicle);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public VehicleJPA findById(Integer id) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        VehicleJPA vehicle = entityManager.find(VehicleJPA.class, id);
        entityManager.close();
        return vehicle;
    }

    public VehicleJPA findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<VehicleJPA> query = entityManager.createNamedQuery("VehicleJPA.findByName", VehicleJPA.class);
        query.setParameter("name", name);
        VehicleJPA vehicle = query.getSingleResult();
        entityManager.close();
        return vehicle;
    }

    public VehicleJPA findByName(DepotJPA depot) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<VehicleJPA> query = entityManager.createNamedQuery("VehicleJPA.findByDepot", VehicleJPA.class);
        query.setParameter("depot", depot);
        VehicleJPA vehicle = query.getSingleResult();
        entityManager.close();
        return vehicle;
    }

    public List<VehicleJPA> findAll() {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<VehicleJPA> query = entityManager.createNamedQuery("VehicleJPA.findAll", VehicleJPA.class);
        List<VehicleJPA> vehicles = query.getResultList();
        entityManager.close();
        return vehicles;
    }

    /*public List<VehicleJPA> findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<VehicleJPA> query = entityManager.createNamedQuery("VehicleJPA.findByName", VehicleJPA.class);
        query.setParameter("name", name);
        List<VehicleJPA> vehicles = query.getResultList();
        entityManager.close();
        return vehicles;
    }*/
}