package io.javabrains.springboot.swagger2.api.controller;


import io.javabrains.springboot.swagger2.api.model.Client;
import io.javabrains.springboot.swagger2.service.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all clients")
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/add-client", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Add a new client")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        clientService.save(client);
        return new ResponseEntity<>(client, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete client by ID")
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update client's name")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody String newName) {
        return new ResponseEntity<>(clientService.update(id, newName), new HttpHeaders(), HttpStatus.OK);
    }
}
