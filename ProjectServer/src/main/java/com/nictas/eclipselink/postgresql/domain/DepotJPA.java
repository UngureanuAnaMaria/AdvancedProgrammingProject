package com.nictas.eclipselink.postgresql.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "depots")
@NamedQueries({
        @NamedQuery(name = "DepotJPA.findAll",
                query = "select e from DepotJPA e order by e.name"),
        @NamedQuery(name = "DepotJPA.findByName",
                query = "SELECT a FROM DepotJPA a WHERE a.name = :name"),
        @NamedQuery(name = "DepotJPA.findById",
                query = "SELECT a FROM DepotJPA a WHERE a.id = :id")
})
public class DepotJPA implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "id", strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VehicleJPA> vehicles = new HashSet<>();

    public DepotJPA() {
        // Required by JPA.
    }

    public DepotJPA(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleJPA[] getVehicles() {
        return vehicles.toArray(new VehicleJPA[0]);
    }

    public void setVehicles(Set<VehicleJPA> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "DepotJPA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof DepotJPA)) return false;
        DepotJPA comp = (DepotJPA) obj;
        return ( comp.name.equals(name));
    }
}