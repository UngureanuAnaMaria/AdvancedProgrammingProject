package io.javabrains.springboot.swagger2.service;

import io.javabrains.springboot.swagger2.api.model.Depot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DepotService {
    private static List<Depot> depotList;

    public DepotService() {
        depotList = new ArrayList<>();
        Depot depot1 = new Depot(1, "D1");
        Depot depot2 = new Depot(2, "D2");
        Depot depot3 = new Depot(3, "D3");
        Depot depot4 = new Depot(4, "D4");
        depotList.addAll(Arrays.asList(depot1, depot2, depot3, depot4));
    }

    public Depot getDepot(Integer id) {
        for(Depot depot : depotList) {
            if(id.equals(depot.getId())) {
                return depot;
            }
        }
        return null;
    }

    public static List<Depot> getDepots() {
        return depotList;
    }

    public void save(Depot depot) {
        if(getDepot(depot.getId()) == null) {
            depotList.add(depot);
        }
    }

    public Depot update(Integer id, String newName) {
        Depot depot = getDepot(id);
        depot.setName(newName);
        return depot;
    }

    public void deleteById(Integer id) {
        depotList.remove(getDepot(id));
    }
}
