package com.nictas.eclipselink.postgresql.entity;
import com.nictas.eclipselink.postgresql.domain.*;

import java.util.*;


public class Problem {
    private List<ClientJPA> clients;
    private List<VehicleJPA> vehicles;
    private List<DepotJPA> depots;
    private Map<ClientJPA, VehicleJPA> allocation;
    private int[][] costMatrix;
    private int m;
    private int n;
    private int[][] shortestPath;
    private int[] clientsLocation;
    private int[] vehiclesLocation;
    private int[] depotsLocation;
    private int[][] nextNode;

    public Problem(List<ClientJPA> clients, List<VehicleJPA> vehicles, List<DepotJPA> depots, Map<ClientJPA, VehicleJPA> allocation, int[][] costMatrix, int m, int n) {
        this.depots = depots;
        this.vehicles = vehicles;
        this.clients = clients;
        this.allocation = allocation;
        this.costMatrix = costMatrix;
        this.shortestPath = new int[m*n][m*n];
        this.nextNode = new int[m*n][m*n];
        this.m = m;
        this.n = n;
    }

    public Problem(List<ClientJPA> clients, List<VehicleJPA> vehicles, List<DepotJPA> depots, int[][] costMatrix, int m, int n) {
        this.depots = depots;
        this.vehicles = vehicles;
        this.clients = clients;
        this.allocation = new HashMap<>();
        this.costMatrix = costMatrix;
        this.shortestPath = new int[m*n][m*n];
        this.nextNode = new int[m*n][m*n];
        this.m = m;
        this.n = n;
    }

    public Problem(int m, int n, int[][] costMatrix) {
        this.depots = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.allocation = new HashMap<>();
        this.costMatrix = costMatrix;
        this.shortestPath = new int[m*n][m*n];
        this.nextNode = new int[m*n][m*n];
        this.m = m;
        this.n = n;
    }

    public void addDepot(DepotJPA depot) {
        if(!depots.contains(depot))
            depots.add(depot);
    }

    public void addVehicle(VehicleJPA vehicle) {
        if(!vehicles.contains(vehicle))
            vehicles.add(vehicle);
    }

    public void addClient(ClientJPA client) {
        if(!clients.contains(client))
            clients.add(client);
    }

    public DepotJPA[] getDepots() {
        return depots.toArray(new DepotJPA[0]);
    }

    public ClientJPA[] getClients() {
        return clients.toArray(new ClientJPA[0]);
    }

    public VehicleJPA[] getVehicles(){
        return vehicles.toArray(new VehicleJPA[0]);
    }

    public List<DepotJPA> getDepotsList() {
        return depots;
    }

    public List<ClientJPA> getClientsList() {
        return clients;
    }

    public List<VehicleJPA> getVehiclesList() {
        return vehicles;
    }

    public int[][] getShortestPath() {
        return shortestPath;
    }

    public int getClientsLocation(int index) {
        return clientsLocation[index];
    }

    public int getVehiclesLocation(int index) {
        return vehiclesLocation[index];
    }

    public int getDepotsLocation(int index) {
        return depotsLocation[index];
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public void allocateClientsToVehicles(StringBuilder output) {
        //sort StartTime!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int matrixLength = costMatrix.length - 1;

        VehicleJPA nearestVehicle;

        depotsLocation = new int[depots.size()];
        vehiclesLocation = new int[vehicles.size()];
        int k = 0;
        int w = 0;
        for(DepotJPA depot : depots) {
            depotsLocation[k] = (int) Math.floor(Math.random() * matrixLength);
            VehicleJPA[] vehicleForEveryDepot = depot.getVehicles();
            for(VehicleJPA vehicle : vehicleForEveryDepot) {
                vehiclesLocation[w] = depotsLocation[k];
                w++;
            }
            k++;
        }

        k=0;
        //System.out.println("DEPOTS: ");
        output.append("DEPOTS: ").append("\n");
        for(DepotJPA depot : depots) {
            //System.out.println(depotsLocation[k++]);
            output.append(depotsLocation[k++]).append("\n");
        }

        k=0;
        //System.out.println("VEHICLES: ");
        output.append("VEHICLES: ").append("\n");
        for(VehicleJPA vehicle : vehicles) {
            //System.out.println(vehiclesLocation[k++]);
            output.append(vehiclesLocation[k++]).append("\n");
        }

        clientsLocation = new int[clients.size()];
        k = 0;
        for(ClientJPA client : clients) {
            clientsLocation[k] = (int) Math.floor(Math.random() * matrixLength);
            k++;
        }

        k=0;
        //System.out.println("CLIENTS: ");
        output.append("CLIENTS: ").append("\n");
        for(ClientJPA client : clients) {
            //System.out.println(clientsLocation[k++]);
            output.append(clientsLocation[k++]).append("\n");
        }

        k = 0;
        for(ClientJPA client : clients) {
            nearestVehicle = findNearestVehicle(client, clientsLocation, k, vehiclesLocation);

            int ok = 1;
            for(VehicleJPA vehicle : vehicles) {
                Set<ClientJPA> clientSet = vehicle.getClients();
                for(ClientJPA clientFromSet : clientSet)
                    if (client.equals(clientFromSet))
                        ok = 0;
            }

            if (nearestVehicle != null && ok == 1) {
                allocation.put(client, nearestVehicle);
                nearestVehicle.addClient(client);
            }
            k++;
        }

        k=0;
        //System.out.println("VEHICLES: ");
        output.append("VEHICLES: ").append("\n");
        for(VehicleJPA vehicle : vehicles) {
            //System.out.println(vehiclesLocation[k++]);
            output.append(vehiclesLocation[k++]).append("\n");
        }


        //printMap(clientsLocation, vehiclesLocation, depotsLocation, m, n);

        k = 0;
        for(VehicleJPA vehicle : vehicles) {
            DepotJPA depot = vehicle.getDepot();
            w = 0;
            for(DepotJPA depotJPA : depots) {
                if(depotJPA.equals(depot)) {
                    vehiclesLocation[k] = depotsLocation[w];
                    break;
                }
                w++;
            }
            k++;
        }

        k=0;
        //System.out.println("VEHICLES: ");
        output.append("VEHICLES: ").append("\n");
        for(VehicleJPA vehicle : vehicles) {
            //System.out.println(vehiclesLocation[k++]);
            output.append(vehiclesLocation[k++]).append("\n");
        }

    }

    private VehicleJPA findNearestVehicle(ClientJPA client, int[] clientsLocation, int k, int[] vehiclesLocation) {
        int matrixLength = costMatrix.length - 1;
        int minDistance = Integer.MAX_VALUE;
        VehicleJPA nearestVehicle = null;
        int w = 0;
        for(VehicleJPA vehicle : vehicles) {
            if(shortestPath[vehiclesLocation[w]][clientsLocation[k]] < minDistance) {
                minDistance = shortestPath[vehiclesLocation[w]][clientsLocation[k]];
                nearestVehicle = vehicle;
                vehiclesLocation[w] = clientsLocation[k];
            }
            w++;
        }
        return nearestVehicle;
    }

    public Map<ClientJPA, VehicleJPA> getAllocationProblem() {
        return allocation;
    }

    public void initializeShortestPath() {
        int matrixLength = costMatrix.length;
        for(int i = 0; i < matrixLength; i++)
            for(int j = 0; j < matrixLength; j++)
                if(i==j) {
                    shortestPath[i][j] = 0;
                    nextNode[i][j] = -1;
                }
                else {
                    shortestPath[i][j] = (costMatrix[i][j] == 0) ? Integer.MAX_VALUE : costMatrix[i][j];
                    nextNode[i][j] = (costMatrix[i][j] == 0) ? -1 : j;

                    //!!!!!!!!!!!!!!!!!!!!!!!!!
                    //Improvement
                    //shortestPath[i][j] = (costMatrix[i][j] == 0) ? 0 : costMatrix[i][j];
                }
    }

    public void calculateAllShortestPaths(int m, int n) {
        int matrixLength = costMatrix.length;

        initializeShortestPath();

        //Floyd-Warshall algorithm implementation(O(matrixLength^3))
        for (int k = 0; k < matrixLength; k++) {
            for (int i = 0; i < matrixLength; i++) {
                for (int j = 0; j < matrixLength; j++) {
                    if(shortestPath[i][k]!= Integer.MAX_VALUE && shortestPath[k][j] != Integer.MAX_VALUE &&
                            (shortestPath[i][k] + shortestPath[k][j]) < shortestPath[i][j]) {
                        shortestPath[i][j] = shortestPath[i][k] + shortestPath[k][j];
                        nextNode[i][j] = nextNode[i][k];
                    }
                }
            }
        }


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Improvement(O(matrixLength^(3/2)))
        //grid graph with all weight 1 on edges
        /*int index;
        for (int i = 0; i <= matrixLength/2 + 1; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if(j > m && shortestPath[i][j - 1] == m) index = -1;
                else if(j % m == 0 && j != 0 && i == j - 1) index = m;
                else index = 1;
                if(shortestPath[i][j] == 0 && i != j && j != 0) {
                    shortestPath[i][j] = shortestPath[i][j - 1] + index;
                    shortestPath[j][i] = shortestPath[i][j];
                }
            }
        }*/
    }

    public void printShortestPathMatrix(StringBuilder output) {
        //System.out.println("Shortest Paths Matrix:");
        output.append("Shortest Paths Matrix:" + "\n");
        int matrixLength = costMatrix.length;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (shortestPath[i][j] == Integer.MAX_VALUE) {
                    //System.out.print("INF  ");
                    output.append("INF  ");
                } else {
                    //System.out.print(shortestPath[i][j]+ "  ");
                    output.append(shortestPath[i][j]+ "  ");
                }
            }
            //System.out.println();
            output.append("\n");
        }
    }

    public List<Integer> getPath(int start, int end) {
        List<Integer> path = new ArrayList<>();
        if (shortestPath[start][end] == Integer.MAX_VALUE) {
            return path; //nu exista cale
        }
        int current = start;
        while (current != end) {
            path.add(current);
            current = nextNode[current][end];
            if (current == -1) {
                return new ArrayList<>(); //nu exista cale
            }
        }
        path.add(end);
        return path;
    }

    public void printAllPaths(StringBuilder output) {
        int matrixLength = costMatrix.length;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (i != j && shortestPath[i][j] != Integer.MAX_VALUE) {
                    //System.out.print("Path from " + i + " to " + j + ": ");
                    output.append("Path from " + i + " to " + j + ": ");
                    List<Integer> path = getPath(i, j);
                    for (int node : path) {
                        //System.out.print(node + " ");
                        output.append(node + " ");
                    }
                    //System.out.println();
                    output.append("\n");
                }
            }
        }
    }


    //Textual representation of the map using Unicode symbols
    private void printMap(int[] clientsLocation, int[] vehiclesLocation, int [] depotsLocation, int m, int n) {
        // Top left corner
        System.out.print("\u250c ");

        int dd = 0, vv = 0, cc = 0;
        // Print top row
        for (int i = 0; i < m; i++) {
            int d = 0, v = 0, c = 0;
            int okDepot = 0;
            for(DepotJPA depot : depots) {
                if (depotsLocation[d] == i) {
                    System.out.print("D" + dd + "  ");
                    okDepot = 1;
                    dd++;
                    depotsLocation[d] = -1;
                }
                d++;
            }

            int okVehicle = 0;
            if(okDepot == 0) {
                for (VehicleJPA vehicle : vehicles) {
                    if (vehiclesLocation[v] == i) {
                        System.out.print("V" + vv + "  ");
                        okVehicle = 1;
                        vv++;
                        vehiclesLocation[v] = -1;
                    }
                    v++;
                }
            }

            int okClient = 0;
            if(okDepot == 0 && okVehicle == 0) {
                for(ClientJPA client : clients) {
                    if (clientsLocation[c] == i) {
                        System.out.print("C" + cc + "  ");
                        okClient = 1;
                        cc++;
                        clientsLocation[c] = -1;
                    }
                    c++;
                }
            }

            if(okClient == 0 && okVehicle == 0 && okDepot == 0) {
                System.out.print("    ");
            }
        }
        //Top right corner
        System.out.print("\u2510");

        System.out.println();
        // Print middle rows
        for (int i = 1; i < n - 1; i++) {

            System.out.println("\u2523 ");

            for (int j = 0; j < m; j++) {
                int d = 0, v = 0, c = 0;
                int okDepot = 0;
                for(DepotJPA depot : depots) {
                    if (depotsLocation[d] == j) {
                        System.out.print("D" + dd + "  ");
                        okDepot = 1;
                        dd++;
                        depotsLocation[d] = -1;
                    }
                    d++;
                }

                int okVehicle = 0;
                if(okDepot == 0) {
                    for (VehicleJPA vehicle : vehicles) {
                        if (vehiclesLocation[v] == j) {
                            System.out.print("V" + vv + "  ");
                            okVehicle = 1;
                            vv++;
                            vehiclesLocation[v] = -1;
                        }
                        v++;
                    }
                }

                int okClient = 0;
                if(okDepot == 0 && okVehicle == 0) {
                    for(ClientJPA client : clients) {
                        if (clientsLocation[c] == j) {
                            System.out.print("C" + cc + "  ");
                            okClient = 1;
                            cc++;
                            clientsLocation[c] = -1;
                        }
                        c++;
                    }
                }

                if(okClient == 0 && okVehicle == 0 && okDepot == 0) {
                    System.out.print("    ");
                }
            }

            System.out.print("\u252b");
            System.out.println();
        }

        // Print bottom row
        System.out.print("\u2517 ");

        for (int i = 0; i < m; i++) {
            int d = 0, v = 0, c = 0;
            int okDepot = 0;
            for(DepotJPA depot : depots) {
                if (depotsLocation[d] == i) {
                    System.out.print("D" + dd + "  ");
                    okDepot = 1;
                    dd++;
                    depotsLocation[d] = -1;
                }
                d++;
            }

            int okVehicle = 0;
            if(okDepot == 0) {
                for (VehicleJPA vehicle : vehicles) {
                    if (vehiclesLocation[v] == i) {
                        System.out.print("V" + vv + "  ");
                        okVehicle = 1;
                        vv++;
                        vehiclesLocation[v] = -1;
                    }
                    v++;
                }
            }

            int okClient = 0;
            if(okDepot == 0 && okVehicle == 0) {
                for(ClientJPA client : clients) {
                    if (clientsLocation[c] == i) {
                        System.out.print("C" + cc + "  ");
                        okClient = 1;
                        cc++;
                        clientsLocation[c] = -1;
                    }
                    c++;
                }
            }

            if(okClient == 0 && okVehicle == 0 && okDepot == 0) {
                System.out.print("    ");
            }
        }

        System.out.println("\u251b");
    }
}