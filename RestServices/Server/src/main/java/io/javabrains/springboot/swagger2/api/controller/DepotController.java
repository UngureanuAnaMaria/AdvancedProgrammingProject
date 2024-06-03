package io.javabrains.springboot.swagger2.api.controller;

import io.javabrains.springboot.swagger2.api.model.Depot;
import io.javabrains.springboot.swagger2.service.DepotService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depots")
public class DepotController {

    private DepotService depotService;

    @Autowired
    public DepotController(DepotService depotService) {
        this.depotService = depotService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all depots")
    public ResponseEntity<List<Depot>> getDepots() {
        return new ResponseEntity<>(depotService.getDepots(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/add-depot", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Add a new depot")
    public ResponseEntity<Depot> addDepot(@RequestBody Depot depot) {
        depotService.save(depot);
        return new ResponseEntity<>(depot, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete depot by ID")
    public ResponseEntity<Integer> deleteDepot(@PathVariable Integer id) {
        depotService.deleteById(id);
        return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update depot's name")
    public ResponseEntity<Depot> updateDepot(@PathVariable Integer id, @RequestBody String newName) {
        return new ResponseEntity<>(depotService.update(id, newName), new HttpHeaders(), HttpStatus.OK);
    }
}
