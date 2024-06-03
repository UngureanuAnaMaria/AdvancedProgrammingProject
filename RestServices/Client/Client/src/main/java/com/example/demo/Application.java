package com.example.demo;

import com.example.demo.model.Depot;
import com.example.demo.model.Vehicle;
import com.example.demo.model.ClientModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
        app.run(args);

        // Create a new depot
        Depot newDepot = new Depot(9, "D9");
        ResponseEntity<Depot> createdDepotResponse = new RestTemplate()
                .postForEntity("http://localhost:8080/depots/add-depot", newDepot, Depot.class);
        Depot createdDepot = createdDepotResponse.getBody();
        System.out.println("Created Depot: " + createdDepot);

        Depot newDepot1 = new Depot(10, "D10");
        ResponseEntity<Depot> createdDepotResponse1 = new RestTemplate().postForEntity("http://localhost:8080/depots/add-depot", newDepot1, Depot.class);
        Depot createdDepot1 = createdDepotResponse1.getBody();
        System.out.println("Created Depot: " + createdDepot1);
        Depot newDepot2 = new Depot(11, "D11");
        ResponseEntity<Depot> createdDepotResponse2 = new RestTemplate().postForEntity("http://localhost:8080/depots/add-depot", newDepot2, Depot.class);

        // Update the name of the depot
        String newName = "D2 Updated";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("" + newName + "", headers);
        ResponseEntity<Depot> updatedDepotResponse = new RestTemplate()
                .exchange("http://localhost:8080/depots/{id}", HttpMethod.PUT, requestEntity, Depot.class,
                        2);
        Depot updatedDepot = updatedDepotResponse.getBody();
        System.out.println("Updated Depot: " + updatedDepot);

        // Delete the depot
        new RestTemplate()
                .delete("http://localhost:8080/depots/delete/{id}", 1);

        // Get all depots
        ResponseEntity<Depot[]> depotsResponse = new RestTemplate()
                .getForEntity("http://localhost:8080/depots/all", Depot[].class);
        Depot[] depots = depotsResponse.getBody();
        System.out.println("Depots: " + Arrays.toString(depots));

        // Create a new vehicle
        Vehicle newVehicle = new Vehicle(5, "V9", new Depot(12, "D12"));
        ResponseEntity<Vehicle> createdVehicleResponse = new RestTemplate()
                .postForEntity("http://localhost:8080/vehicles/add-vehicle", newVehicle, Vehicle.class);
        Vehicle createdVehicle = createdVehicleResponse.getBody();
        System.out.println("Created Vehicle: " + createdVehicle);

        Vehicle newVehicle1 = new Vehicle(6, "V6", new Depot(13, "D13"));
        ResponseEntity<Vehicle> createdVehicleResponse1 = new RestTemplate().postForEntity("http://localhost:8080/vehicles/add-vehicle", newVehicle1, Vehicle.class);
        Vehicle newVehicle2 = new Vehicle(7, "V7", new Depot(14, "D14"));
        ResponseEntity<Vehicle> createdVehicleResponse2 = new RestTemplate().postForEntity("http://localhost:8080/vehicles/add-vehicle", newVehicle2, Vehicle.class);

        // Update the name of the vehicle
        newName = "V2 Updated";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity<>("" + newName + "", headers);
        ResponseEntity<Vehicle> updatedVehicleResponse = new RestTemplate()
                .exchange("http://localhost:8080/vehicles/{id}", HttpMethod.PUT, requestEntity, Vehicle.class,
                        2);
        Vehicle updatedVehicle = updatedVehicleResponse.getBody();
        System.out.println("Updated Vehicle: " + updatedVehicle);

        // Delete the vehicle
        new RestTemplate()
                .delete("http://localhost:8080/vehicles/delete/{id}", 1);

        // Get all vehicles
        ResponseEntity<Vehicle[]> vehiclesResponse = new RestTemplate()
                .getForEntity("http://localhost:8080/vehicles/all", Vehicle[].class);
        Vehicle[] vehicles = vehiclesResponse.getBody();
        System.out.println("Vehicles: " + Arrays.toString(vehicles));

        // Create a new client
        ClientModel newClientModel = new ClientModel(5, "C5", 2, 3, ClientType.PREMIUM);
        ResponseEntity<ClientModel> createdClientModelResponse = new RestTemplate()
                .postForEntity("http://localhost:8080/clients/add-client", newClientModel, ClientModel.class);
        ClientModel createdClientModel = createdClientModelResponse.getBody();
        System.out.println("Created ClientModel: " + createdClientModel);

        ClientModel newClientModel1 = new ClientModel(6, "C6", 7, 9, ClientType.REGULAR);
        ResponseEntity<ClientModel> createdClientModelResponse1 = new RestTemplate().postForEntity("http://localhost:8080/clients/add-client", newClientModel1, ClientModel.class);
        ClientModel newClientModel2 = new ClientModel(7, "C7", 10, 11, ClientType.REGULAR);
        ResponseEntity<ClientModel> createdClientModelResponse2 = new RestTemplate().postForEntity("http://localhost:8080/clients/add-client", newClientModel2, ClientModel.class);

        // Update the name of the client
        newName = "C2 Updated";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity<>("" + newName + "", headers);
        ResponseEntity<ClientModel> updatedClientModelResponse = new RestTemplate()
                .exchange("http://localhost:8080/clients/{id}", HttpMethod.PUT, requestEntity, ClientModel.class,
                        2);
        ClientModel updatedClientModel = updatedClientModelResponse.getBody();
        System.out.println("Updated ClientModel: " + updatedClientModel);

        // Delete the client
        new RestTemplate()
                .delete("http://localhost:8080/clients/delete/{id}", 1);

        // Get all clients
        ResponseEntity<ClientModel[]> clientsResponse = new RestTemplate()
                .getForEntity("http://localhost:8080/clients/all", ClientModel[].class);
        ClientModel[] clients = clientsResponse.getBody();
        System.out.println("ClientModels: " + Arrays.toString(clients));
    }
}