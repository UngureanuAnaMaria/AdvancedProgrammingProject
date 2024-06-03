package io.javabrains.springboot.swagger2.api.controller;


import io.javabrains.springboot.swagger2.api.model.Vehicle;
import io.javabrains.springboot.swagger2.service.VehicleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all vehicles")
    public ResponseEntity<List<Vehicle>> getVehicles() {
        return new ResponseEntity<>(vehicleService.getVehicles(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/add-vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Add a new vehicle")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.save(vehicle);
        return new ResponseEntity<>(vehicle, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete vehicle by ID")
    public ResponseEntity<Integer> deleteVehicle(@PathVariable Integer id) {
        vehicleService.deleteById(id);
        return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update vehicle's name")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer id, @RequestBody String newName) {
        return new ResponseEntity<>(vehicleService.update(id, newName), new HttpHeaders(), HttpStatus.OK);
    }
}
