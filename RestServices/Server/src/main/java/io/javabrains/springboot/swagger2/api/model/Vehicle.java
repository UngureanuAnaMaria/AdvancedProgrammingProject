package io.javabrains.springboot.swagger2.api.model;

public class Vehicle {
    private int id;

    private String name;
    private Depot depot;

    public Vehicle(int id, String name, Depot depot) {
        this.id = id;
        this.name = name;
        this.depot = depot;
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

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
}
