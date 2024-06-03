package com.nictas.eclipselink.postgresql.entity;

import com.nictas.eclipselink.postgresql.domain.ClientJPA;
import com.nictas.eclipselink.postgresql.domain.DepotJPA;
import com.nictas.eclipselink.postgresql.domain.VehicleJPA;
import com.nictas.eclipselink.postgresql.entity.ClientType;
import com.nictas.eclipselink.postgresql.entity.Problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomProblemGenerator {
    private List<ClientJPA> clients;
    private List<VehicleJPA> vehicles;
    private List<DepotJPA> depots;
    private int[][] costMatrix;
    private int m;
    private int n;

    private Problem problem;

    public RandomProblemGenerator() {
        this.m = (int)Math.floor(Math.random()*(10 - 2 + 1) + 2);//m at least 2
        this.n = (int)Math.floor(Math.random()*(10 - 2 + 1) + 2);//n at least 2
        this.costMatrix = new int[this.m * this.n][this.m * this.n];
        generateRandomCostMatrix();
        problem = new Problem(this.m, this.n, this.costMatrix);
        generateProblem();
    }

    public Problem getProblem() {
        return problem;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int[][] getCostMatrix() {
        return costMatrix;
    }

    public void generateProblem() {
        //Integer.MAX_VALUE instead of 100 => ERROR
        int depotsRandomNumber = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1); // at least 1 depot
        char indexChar = 'A';
        for (int i = 0; i < depotsRandomNumber; i++) {
            String indexStringValue = String.valueOf(indexChar);
            String name = "Depot " + indexStringValue;
            problem.addDepot(new DepotJPA(name));
            //depots.add(new DepotJPA(name));
            indexChar++;
        }

        int vehiclesRandomNumber = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1); // at least 1 vehicle
        int indexInt = 1;
        for (int i = 0; i < vehiclesRandomNumber; i++) {
            String indexStringValue = String.valueOf(indexInt);
            String name = "Vehicle " + indexStringValue;
            int depotNumber = (int) Math.floor(Math.random() * (depotsRandomNumber - 1 - 1 + 1) + 1);
            char indexCharVehicle = (char)('A' + depotNumber);
            String depotName = "Depot " + indexCharVehicle;
            DepotJPA depot = new DepotJPA(depotName);
            problem.addVehicle(new VehicleJPA(name, depot));
            //depot.addVehicle(new VehicleJPA(name, depot));
            //vehicles.add(new VehicleJPA(name, depot));
            indexInt++;
        }

        int clientRandomNumber = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1); // at least 1 client
        indexInt = 1;
        for(int i = 0; i < clientRandomNumber; i++) {
            String indexStringValue = String.valueOf(indexInt);
            String name = "Client " + indexStringValue;
            int start = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            int end = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            while(start > end) {
                end = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            }
            //for LocalTime
            /*
            int hourStart = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            int minutesStart = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            int secondsStart = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            LocalTime start = LocalTime.of(hourStart, minutesStart, secondsStart);
            int hourEnd = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            while(hourStart > hourEnd) {
                hourEnd = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            }
            int minutesEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            if(hourStart == hourEnd) {
                while(minutesStart > minutesEnd)
                    minutesEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            }
            int secondsEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            if(minutesStart == minutesEnd) {
                while(secondsStart > secondsEnd)
                    secondsEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            }
            LocalTime end = LocalTime.of(hourEnd, minutesEnd, secondsEnd);*/
            int regularOrPremium = (int) Math.floor(Math.random() * (1 - 0 + 1) + 0);//1 regular, 0 premium

            if(regularOrPremium == 1) {
                problem.addClient(new ClientJPA(name, start, end, ClientType.REGULAR));
                //clients.add(new ClientJPA(name, start, end, ClientType.REGULAR));
            } else {
                problem.addClient(new ClientJPA(name, start, end, ClientType.PREMIUM));
                //clients.add(new ClientJPA(name, start, end, ClientType.PREMIUM));
            }
            indexInt++;
        }
    }


    public void generateRandomCostMatrix() {
        int matrixLength = costMatrix.length;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (i == j) this.costMatrix[i][j] = 0;
                else if((i+1) % this.m == 0 && (j == i + this.m || j == i - 1 || j == i - this.m)) {
                    this.costMatrix[i][j] = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                } else if(i % this.m == 0 && (j == i + 1 || j == i + this.m || j == i - this.m)) {
                    this.costMatrix[i][j] = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                }
                else if((i % this.m != 0 && (i + 1) % this.m != 0) && (j == i + 1 || j == i + this.m || j == i - this.m || j == i - 1)) {
                    this.costMatrix[i][j] = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                } else {
                    this.costMatrix[i][j] = 0;
                }
            }
        }
    }

    //improvement
    public void generateRandomCostMatrixAllWeightOne() {
        int matrixLength = costMatrix.length;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (i == j) costMatrix[i][j] = 0;
                else if ((i + 1) % m == 0 && (j == i + m || j == i - 1 || j == i - m)) {
                    costMatrix[i][j] = 1;
                } else if (i % m == 0 && (j == i + 1 || j == i + m || j == i - m)) {
                    costMatrix[i][j] = 1;
                } else if ((i % m != 0 && (i + 1) % m != 0) && (j == i + 1 || j == i + m || j == i - m || j == i - 1)) {
                    costMatrix[i][j] = 1;
                } else {
                    costMatrix[i][j] = 0;
                }
            }
        }
    }
}