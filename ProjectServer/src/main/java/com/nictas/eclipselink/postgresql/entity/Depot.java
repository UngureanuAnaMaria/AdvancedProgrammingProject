package com.nictas.eclipselink.postgresql.entity;

public class Depot {
    private String name;
    private Vehicle[] vehicles;
    public Depot(String name){
        this.name = name;
        this.vehicles = new Vehicle[0];
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setVehicles(Vehicle ...vehicles) {
        this.vehicles = vehicles;
        for (Vehicle v : vehicles) {
            v.setDepot(this);
        }
    }

    //!!!!!!!!!!!!!!!!!!!!!!
    public void addVehicle(Vehicle vehicle) {
        Vehicle[] newVehicles = new Vehicle[vehicles.length + 1];

        for (int i = 0; i < vehicles.length; i++) {
            newVehicles[i] = vehicles[i];
        }

        newVehicles[vehicles.length] = vehicle;

        vehicles = newVehicles;
    }

    public Vehicle[] getVehicles() {
        return this.vehicles;
    }

    @Override
    public String toString() {
        return "Depot{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Depot)) return false;
        Depot comp = (Depot) obj;
        return ( comp.name.equals(name));
    }
}