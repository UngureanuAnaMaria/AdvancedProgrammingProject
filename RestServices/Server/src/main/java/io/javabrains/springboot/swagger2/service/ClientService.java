package io.javabrains.springboot.swagger2.service;

import io.javabrains.springboot.swagger2.ClientType;
import io.javabrains.springboot.swagger2.api.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {
    private static List<Client> clientList;

    public ClientService() {
        clientList = new ArrayList<>();
        Client client1 = new Client(1, "C1", 1, 2, ClientType.REGULAR);
        Client client2 = new Client(2, "C2", 2, 3, ClientType.PREMIUM);
        Client client3 = new Client(3, "C3", 5, 6, ClientType.REGULAR);
        Client client4 = new Client(4, "C4", 7, 8, ClientType.PREMIUM);
        clientList.addAll(Arrays.asList(client1, client2, client3, client4));
    }

    public Client getClient(Integer id) {
        for(Client client : clientList) {
            if(id.equals(client.getId())) {
                return client;
            }
        }
        return null;
    }

    public static List<Client> getClients() {
        return clientList;
    }

    public void save(Client client) {
        if(getClient(client.getId()) == null) {
            clientList.add(client);
        }
    }

    public Client update(Integer id, String newName) {
        Client client = getClient(id);
        client.setName(newName);
        return client;
    }

    public void deleteById(Integer id) {
        clientList.remove(getClient(id));
    }
}
