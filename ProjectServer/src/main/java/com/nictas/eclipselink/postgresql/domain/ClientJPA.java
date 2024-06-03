package com.nictas.eclipselink.postgresql.domain;
import com.nictas.eclipselink.postgresql.entity.ClientType;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "clients")
@NamedQueries({
        @NamedQuery(name = "ClientJPA.findAll",
                query = "select e from ClientJPA e order by e.name"),
        @NamedQuery(name = "ClientJPA.findByName",
                query = "SELECT a FROM ClientJPA a WHERE a.name = :name"),
        @NamedQuery(name = "ClientJPA.findById",
                query = "SELECT a FROM ClientJPA a WHERE a.id = :id"),
        @NamedQuery(name = "ClientJPA.findByStartTime",
                query = "SELECT a FROM ClientJPA a WHERE a.startTime = :startTime"),
        @NamedQuery(name = "ClientJPA.findByEndTime",
                query = "SELECT a FROM ClientJPA a WHERE a.endTime = :endTime"),
        @NamedQuery(name = "ClientJPA.findByType",
                query = "SELECT a FROM ClientJPA a WHERE a.type = :type")
})
public class ClientJPA implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "id", strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "startTime")
    private Integer startTime;

    @Column(name = "endTime")
    private Integer endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ClientType type;

    /*@ManyToMany(mappedBy = "clients")
    private Set<VehicleJPA> vehicles = new HashSet<>();*/

    public ClientJPA() {
        // Required by JPA.
    }

    public ClientJPA(String name, Integer startTime, Integer endTime, ClientType type) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
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

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ClientJPA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", type=" + type +
                '}';
    }
}