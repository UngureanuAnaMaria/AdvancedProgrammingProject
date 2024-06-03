package com.nictas.eclipselink.postgresql.exec;
import com.nictas.eclipselink.postgresql.domain.*;
import com.nictas.eclipselink.postgresql.entity.Problem;
import com.nictas.eclipselink.postgresql.entity.Solution;
import com.nictas.eclipselink.postgresql.gui.MainFrame;
import com.nictas.eclipselink.postgresql.repos.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;

public class ExecuteProblem {
    public static String execute() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        /*entityManager.persist(new DepotJPA("Depot A"));
        entityManager.persist(new DepotJPA("Depot B"));
        transaction.commit();*/

        /*entityManager.persist(new VehicleJPA("Vehicle 1", new DepotJPA("Depot C")));
        transaction.commit();
        entityManager.persist(new VehicleJPA("Vehicle 2", new DepotJPA("Depot D")));
        transaction.commit();
        entityManager.persist(new VehicleJPA("Vehicle 3", new DepotJPA("Depot E")));
        transaction.commit();
        entityManager.persist(new VehicleJPA("Vehicle 4", new DepotJPA("Depot F")));
        transaction.commit();*/

        /*entityManager.persist(new ClientJPA("Client 1", 10, 11, ClientType.REGULAR));
        transaction.commit();
        entityManager.persist(new ClientJPA("Client 2", 11, 12, ClientType.REGULAR));
        transaction.commit();*/

        /*DepotJPA newDepot = new DepotJPA("New Depot");
        entityManager.persist(newDepot);
        transaction.commit();*/

        /*DepotJPA depot = (DepotJPA) entityManager.createQuery(
                        "select e from DepotJPA e where e.name='New Depot'")
                .getSingleResult();
        depot.setName("Depot H");
        transaction.commit();*/

        DepotJPA depot = entityManager.find(DepotJPA.class, 15);
        //System.out.println(depot.getName());

        DepotJPA foundDepot = DepotRepository.getInstance().findById(depot.getId());
        //System.out.println("Found Author: " + foundDepot);

        VehicleJPA vehicle = entityManager.find(VehicleJPA.class, 1);
        //System.out.println(vehicle);

        ClientJPA client = entityManager.find(ClientJPA.class, 1);
        //System.out.println(client);

        vehicle.addClient(client);

        //System.out.println(vehicle);

        /*int m = 3, n = 2;

        int[][] costMatrix = {
                {0, 1, 0, 2, 0, 0},
                {1, 0, 4, 0, 5, 0},
                {0, 2, 0, 0, 0, 3},
                {1, 0, 0, 0, 6, 0},
                {0, 2, 0, 6, 0, 3},
                {0, 0, 1, 0, 2, 0}
        };*/

        int m = 3, n = 4;

        /*int[][] costMatrix = {
                //  0  1  2  3  4  5  6  7  8  9  10 11
                { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0,  0, 0}, // 0
                { 1, 0, 1, 0, 1, 0, 0, 0, 0, 0,  0, 0}, // 1
                { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0,  0, 0}, // 2
                { 1, 0, 0, 0, 1, 0, 1, 0, 0, 0,  0, 0}, // 3
                { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0,  0, 0}, // 4
                { 0, 0, 1, 0, 1, 0, 0, 0, 1, 0,  0, 0}, // 5
                { 0, 0, 0, 1, 0, 0, 0, 1, 0, 1,  0, 0}, // 6
                { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0,  1, 0}, // 7
                { 0, 0, 0, 0, 0, 1, 0, 1, 0, 0,  0, 1}, // 8
                { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,  1, 0}, // 9
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1,  0, 1}, // 10
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,  1, 0}  // 11
        };*/

        int[][] costMatrix = {
                //  0  1  2  3  4  5  6  7  8  9  10 11
                { 0, 5, 0, 2, 0, 0, 0, 0, 0, 0,  0, 0},  // 0
                { 5, 0, 3, 0, 7, 0, 0, 0, 0, 0,  0, 0},  // 1
                { 0, 3, 0, 0, 0, 4, 0, 0, 0, 0,  0, 0},  // 2
                { 2, 0, 0, 0, 6, 0, 8, 0, 0, 0,  0, 0},  // 3
                { 0, 7, 0, 6, 0, 2, 0, 5, 0, 0,  0, 0},  // 4
                { 0, 0, 4, 0, 2, 0, 0, 0, 9, 0,  0, 0},  // 5
                { 0, 0, 0, 8, 0, 0, 0, 3, 0, 6,  0, 0},  // 6
                { 0, 0, 0, 0, 5, 0, 3, 0, 1, 0,  2, 0},  // 7
                { 0, 0, 0, 0, 0, 9, 0, 1, 0, 0,  0, 4},  // 8
                { 0, 0, 0, 0, 0, 0, 6, 0, 0, 0,  3, 0},  // 9
                { 0, 0, 0, 0, 0, 0, 0, 2, 0, 3,  0, 7},  // 10
                { 0, 0, 0, 0, 0, 0, 0, 0, 4, 0,  7, 0}   // 11
        };





        //for improvement
        /*int[][] costMatrix = {
                {0, 1, 0, 1, 0, 0},
                {1, 0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1},
                {0, 0, 1, 0, 1, 0}
        };*/

        StringBuilder output = new StringBuilder();
        Problem problem = new Problem(m, n, costMatrix);

        List<ClientJPA> clients= ClientRepository.getInstance().findAll();
        //System.out.println("Clients: " + clients);
        output.append("Clients: ").append(clients).append("\n");

        for(ClientJPA clientProblem : clients) {
            problem.addClient(clientProblem);
        }

        ClientJPA[] clientsProblem = problem.getClients();
        for(ClientJPA clientProblem : clientsProblem) {
            //System.out.println(clientProblem);
            output.append(clientProblem).append("\n");
        }

        List<DepotJPA> depots= DepotRepository.getInstance().findAll();
        //System.out.println("Depots: " + depots);
        output.append("Depots: " + depots + "\n");


        for(DepotJPA depotProblem : depots) {
            problem.addDepot(depotProblem);
        }

        DepotJPA[] depotsProblem = problem.getDepots();
        for(DepotJPA depotProblem : depotsProblem) {
            //System.out.println(depotProblem);
            output.append(depotProblem + "\n");
        }

        List<VehicleJPA> vehicles = VehicleRepository.getInstance().findAll();
        //System.out.println("Vehicles: " + vehicles);
        output.append("Vehicles: " + vehicles + "\n");

        for (VehicleJPA vehicleProblem : vehicles) {
            problem.addVehicle(vehicleProblem);
        }

        VehicleJPA[] vehiclesProblem = problem.getVehicles();
        for (VehicleJPA vehicleProblem : vehiclesProblem) {
            //System.out.println(vehicleProblem);
            output.append(vehicleProblem + "\n");
        }

        problem.calculateAllShortestPaths(m, n);
        problem.printShortestPathMatrix(output);

        problem.allocateClientsToVehicles(output);

        Solution solution = new Solution(problem);

        Map<ClientJPA, VehicleJPA> allocation = problem.getAllocationProblem();
        solution.setAllocation(allocation);

        System.out.println();
        //System.out.println("ALLOCATION : ");
        output.append("ALLOCATION : ").append("\n");

        solution.printAllocation(output);

        problem.printAllPaths(output);

        MainFrame frame = new MainFrame(problem, output);
        frame.setVisible(true);
        frame.saveAsImage("C:\\Users\\anaun\\OneDrive\\Desktop\\vehicleRoutingProblem1.png");

        //return output.toString();
        return output.toString();
    }

}