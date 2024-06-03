package io.javabrains.springboot.swagger2.service;


import io.javabrains.springboot.swagger2.api.model.Depot;
import io.javabrains.springboot.swagger2.api.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VehicleService {
    private static List<Vehicle> vehicleList;

    public VehicleService() {
        vehicleList = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle(1, "V1", new Depot(5, "D5"));
        Vehicle vehicle2 = new Vehicle(2, "V2", new Depot(6, "D6"));
        Vehicle vehicle3 = new Vehicle(3, "V3", new Depot(7, "D7"));
        Vehicle vehicle4 = new Vehicle(4, "V4", new Depot(8, "D8"));
        vehicleList.addAll(Arrays.asList(vehicle1, vehicle2, vehicle3, vehicle4));
    }

    public Vehicle getVehicle(Integer id) {
        for(Vehicle vehicle : vehicleList) {
            if(id.equals(vehicle.getId())) {
                return vehicle;
            }
        }
        return null;
    }

    public static List<Vehicle> getVehicles() {
        return vehicleList;
    }

    public void save(Vehicle vehicle) {
        if(getVehicle(vehicle.getId()) == null) {
            vehicleList.add(vehicle);
        }
    }

    public Vehicle update(Integer id, String newName) {
        Vehicle vehicle = getVehicle(id);
        vehicle.setName(newName);
        return vehicle;
    }

    public void deleteById(Integer id) {
        vehicleList.remove(getVehicle(id));
    }
}

