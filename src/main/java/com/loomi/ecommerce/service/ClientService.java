package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.Client;
import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client save(Client client){

        client.setId(null);
        return clientRepository.save(client);
    }

    public Client findById(Long id) {
        Optional<Client> optionalClient=  clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    public List<Client> findByFullName(String fullName) {
        return clientRepository
                .findByFullNameContainingIgnoreCase(fullName);
    }

    public List<Order> findListOrderByCLientId(Long id) {
        Optional<Client> optionalClient =  clientRepository.findById(id);
        return optionalClient.get().getOrders();
    }

    public Client update(Client client) {
        Client  userFound = findById(client.getId());
        if (userFound != null) {
            return clientRepository.save(client);
        }else{
            return client;
        }
    }

    public void deleteById(Long id) {
        Client client = new Client();
        client.setId(id);
        clientRepository.delete(client);

    }

}
