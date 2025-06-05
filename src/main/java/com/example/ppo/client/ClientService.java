package com.example.ppo.client;

import com.example.ppo.exception.client.*;
import com.example.ppo.message.Message;
import com.example.ppo.order.Order;
import com.example.ppo.review.Review;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClientService implements IClientService {
    private final IClientRepository clientRepo;

    public ClientService(IClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    @Transactional
    public Client registerClient(Client client) 
            throws ClientValidationException, ClientAlreadyExistsException, ClientOperationException {
        if (!client.isValid()) {
            log.warn("Invalid client");
            throw new ClientValidationException("Invalid client data");
        }

        try {
            if (clientRepo.existsByLogin(client.getLogin())) {
                log.warn("Client with login={} already exists", client.getLogin());
                throw new ClientAlreadyExistsException(client.getLogin());
            }
            client = clientRepo.save(client);
            log.info("Saved client with login={}", client.getLogin());
            return client;
        } catch (Exception e) {
            log.error("Failed to register client", e);
            throw new ClientOperationException("Failed to register client: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientByLogin(String login) 
            throws ClientNotFoundException, ClientOperationException {
        try {
            if (!clientRepo.existsByLogin(login)) {
                log.warn("Client with login={} not found", login);
                throw new ClientAlreadyExistsException(login);
            }
            Client client = clientRepo.findByLogin(login).get();
            log.info("Got client with login={}", login);
            return client;
        } catch (Exception e) {
            log.error("Failed to get client", e);
            throw new ClientOperationException("Failed to get client: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Client updateClient(Client client) 
            throws ClientNotFoundException, ClientValidationException, ClientOperationException {
        if (!client.isValid()) {
            log.warn("Invalid client");
            throw new ClientValidationException("Invalid client data");
        }

        try {
            if (!clientRepo.existsByLogin(client.getLogin())) {
                log.warn("Client with login={} already exists", client.getLogin());
                throw new ClientNotFoundException(client.getLogin());
            }
            client = clientRepo.update(client);
            log.info("Updated client with login={}", client.getLogin());
            return client;
        } catch (Exception e) {
            log.error("Failed to update client", e);
            throw new ClientOperationException("Failed to update client: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteClient(String login) 
            throws ClientNotFoundException, ClientOperationException {
        try {
            if (!clientRepo.existsByLogin(login)) {
                log.warn("Client with login={} is not found", login);
                throw new ClientNotFoundException(login);
            }
            clientRepo.deleteByLogin(login);
            log.info("Deleted client with login={}", login);
        } catch (Exception e) {
            log.error("Failed to update client with", e);
            throw new ClientOperationException("Failed to delete client: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean clientExists(String login) 
            throws ClientOperationException {
        try {
            return clientRepo.existsByLogin(login);
        } catch (Exception e) {
            log.error("Failed to check client exists with", e);
            throw new ClientOperationException("Failed to check client exists: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getClientOrders(Client client) {
        return client.getOrders();
    }

    @Override
    public List<Message> getClientMessages(Client client) {
        return client.getMessages();
    }

    @Override
    public List<Review> getClientReviews(Client client) {
        return client.getReviews();
    }
}