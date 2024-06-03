package com.nictas.eclipselink.postgresql.entity;
import com.nictas.eclipselink.postgresql.domain.ClientJPA;
import com.nictas.eclipselink.postgresql.domain.VehicleJPA;
import com.nictas.eclipselink.postgresql.entity.Problem;

import java.util.Map;
import java.util.HashMap;

public class Solution {
    //imbunatatiri:afis clienti, depots, vehicles
    private Map<ClientJPA, VehicleJPA> allocation;
    private Problem problem;

    public Solution(Problem problem){
        this.allocation = new HashMap<>();
        this.problem = problem;
    }

    public Solution(Map<ClientJPA, VehicleJPA> allocation) {
        this.allocation = allocation;
    }
    public Map<ClientJPA, VehicleJPA> getAllocationSolution() {
        return allocation;
    }

    public void setAllocation(Map<ClientJPA, VehicleJPA> allocation) {
        this.allocation = allocation;
    }

    public void printAllocation(StringBuilder output) {
        //!!!!!!!!!!!!!!!!!!!!!!!
        for(Map.Entry<ClientJPA, VehicleJPA> entry : allocation.entrySet())
            //System.out.println("Client : " + entry.getKey() + ", vehicle : " + entry.getValue());
            output.append("Client : " + entry.getKey() + ", vehicle : " + entry.getValue() + "\n");
    }
}