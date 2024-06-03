package com.nictas.eclipselink.postgresql.exec;

import com.nictas.eclipselink.postgresql.domain.ClientJPA;
import com.nictas.eclipselink.postgresql.domain.DepotJPA;
import com.nictas.eclipselink.postgresql.domain.VehicleJPA;
import com.nictas.eclipselink.postgresql.entity.Problem;
import com.nictas.eclipselink.postgresql.entity.RandomProblemGenerator;
import com.nictas.eclipselink.postgresql.entity.Solution;
import com.nictas.eclipselink.postgresql.gui.MainFrame;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;

public class ExecuteRandomProblem {
    public static String execute() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        RandomProblemGenerator randomProblemGenerated = new RandomProblemGenerator();
        Problem randomProblem = randomProblemGenerated.getProblem();

        StringBuilder output = new StringBuilder();

        List<ClientJPA> clients = randomProblem.getClientsList();
        //System.out.println("Clients: " + clients);
        output.append("Clients: ").append(clients).append("\n");

        for(ClientJPA clientProblem : clients) {
            randomProblem.addClient(clientProblem);
        }

        ClientJPA[] clientsProblem = randomProblem.getClients();
        for(ClientJPA clientProblem : clientsProblem) {
            //System.out.println(clientProblem);
            output.append(clientProblem).append("\n");
        }

        List<DepotJPA> depots = randomProblem.getDepotsList();
        //System.out.println("Depots: " + depots);
        output.append("Depots: " + depots + "\n");


        for(DepotJPA depotProblem : depots) {
            randomProblem.addDepot(depotProblem);
        }

        DepotJPA[] depotsProblem = randomProblem.getDepots();
        for(DepotJPA depotProblem : depotsProblem) {
            //System.out.println(depotProblem);
            output.append(depotProblem + "\n");
        }

        List<VehicleJPA> vehicles = randomProblem.getVehiclesList();
        //System.out.println("Vehicles: " + vehicles);
        output.append("Vehicles: " + vehicles + "\n");

        for (VehicleJPA vehicleProblem : vehicles) {
            randomProblem.addVehicle(vehicleProblem);
        }

        VehicleJPA[] vehiclesProblem = randomProblem.getVehicles();
        for (VehicleJPA vehicleProblem : vehiclesProblem) {
            //System.out.println(vehicleProblem);
            output.append(vehicleProblem + "\n");
        }

        int m = randomProblem.getM();
        int n = randomProblem.getN();
        randomProblem.calculateAllShortestPaths(m, n);
        randomProblem.printShortestPathMatrix(output);

        randomProblem.allocateClientsToVehicles(output);

        Solution solution = new Solution(randomProblem);

        Map<ClientJPA, VehicleJPA> allocation = randomProblem.getAllocationProblem();
        solution.setAllocation(allocation);

        System.out.println();
        //System.out.println("ALLOCATION : ");
        output.append("ALLOCATION : ").append("\n");

        solution.printAllocation(output);

        randomProblem.printAllPaths(output);

        MainFrame frame = new MainFrame(randomProblem, output);
        frame.setVisible(true);
        frame.saveAsImage("C:\\Users\\anaun\\OneDrive\\Desktop\\vehicleRoutingProblem2.png");

        output.append(randomProblem.getM()).append("\n");
        output.append(randomProblem.getN()).append("\n");
        //return output.toString();
        return output.toString();
    }
}