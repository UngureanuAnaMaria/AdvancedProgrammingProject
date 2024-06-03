package com.nictas.eclipselink.postgresql.repos;
import com.nictas.eclipselink.postgresql.entity.ClientType;
import com.nictas.eclipselink.postgresql.domain.ClientJPA;
import com.nictas.eclipselink.postgresql.utils.EntityManagerFactorySingleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClientRepository {
    private EntityManager entityManager;

    private static final ClientRepository instance = new ClientRepository();

    private ClientRepository() {
    }

    public static ClientRepository getInstance() {
        return instance;
    }

    public void create(ClientJPA client) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public ClientJPA findById(Integer id) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        ClientJPA client = entityManager.find(ClientJPA.class, id);
        entityManager.close();
        return client;
    }

    public ClientJPA findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<ClientJPA> query = entityManager.createNamedQuery("ClientJPA.findByName", ClientJPA.class);
        query.setParameter("name", name);
        ClientJPA client = query.getSingleResult();
        entityManager.close();
        return client;
    }

    public ClientJPA findByStartTime(int startTime) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<ClientJPA> query = entityManager.createNamedQuery("ClientJPA.findByStartTime", ClientJPA.class);
        query.setParameter("startTime", startTime);
        ClientJPA client = query.getSingleResult();
        entityManager.close();
        return client;
    }

    public ClientJPA findByEndTime(int endTime) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<ClientJPA> query = entityManager.createNamedQuery("ClientJPA.findByEndTime", ClientJPA.class);
        query.setParameter("endTime", endTime);
        ClientJPA client = query.getSingleResult();
        entityManager.close();
        return client;
    }

    public ClientJPA findByType(ClientType type) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<ClientJPA> query = entityManager.createNamedQuery("ClientJPA.findByType", ClientJPA.class);
        query.setParameter("type", type);
        ClientJPA client = query.getSingleResult();
        entityManager.close();
        return client;
    }

    public List<ClientJPA> findAll() {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<ClientJPA> query = entityManager.createNamedQuery("ClientJPA.findAll", ClientJPA.class);
        List<ClientJPA> clients = query.getResultList();
        entityManager.close();
        return clients;
    }

    /*public List<ClientJPA> findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<ClientJPA> query = entityManager.createNamedQuery("ClientJPA.findByName", ClientJPA.class);
        query.setParameter("name",  name);
        List<ClientJPA> clients = query.getResultList();
        entityManager.close();
        return clients;
    }*/
}