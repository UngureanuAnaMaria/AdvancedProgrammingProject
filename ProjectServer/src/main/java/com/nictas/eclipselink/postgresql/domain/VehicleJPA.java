package com.nictas.eclipselink.postgresql.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "VehicleJPA.findAll",
                query = "select e from VehicleJPA e order by e.name"),
        @NamedQuery(name = "VehicleJPA.findByName",
                query = "SELECT a FROM VehicleJPA a WHERE a.name = :name"),
        @NamedQuery(name = "VehicleJPA.findById",
                query = "SELECT a FROM VehicleJPA a WHERE a.id = :id"),
        @NamedQuery(name = "VehicleJPA.findByDepot",
                query = "SELECT a FROM VehicleJPA a WHERE a.depot = :depot")
})
public class VehicleJPA implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "id", strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "depot_name", referencedColumnName = "name", nullable = false)
    private DepotJPA depot;

    @ManyToMany
    @JoinTable(
            name = "vehicle_clients",
            joinColumns = @JoinColumn(name = "vehicle_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "client_name", referencedColumnName = "name")
    )
    private Set<ClientJPA> clients = new HashSet<>();

    public VehicleJPA() {
    }

    public VehicleJPA(String name, DepotJPA depot) {
        this.name = name;
        this.depot = depot;
    }

    public VehicleJPA(String name, DepotJPA depot, Set<ClientJPA> clients) {
        this.name = name;
        this.depot = depot;
        this.clients = clients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepotJPA getDepot() {
        return depot;
    }

    public void setDepot(DepotJPA depot) {
        this.depot = depot;
    }

    public Set<ClientJPA> getClients() {
        return clients;
    }

    public void setClients(Set<ClientJPA> clients) {
        this.clients = clients;
    }

    public void addClient(ClientJPA client) {
        this.clients.add(client);
    }

    @Override
    public String toString() {
        return "VehicleJPA{" +
                "name='" + name + '\'' +
                ", depot=" + depot +
                ", clients=" + clients +
                '}';
    }
}