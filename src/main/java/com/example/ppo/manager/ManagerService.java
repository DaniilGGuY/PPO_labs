package com.example.ppo.manager;

import com.example.ppo.exception.manager.*;
import com.example.ppo.product.Product;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ManagerService implements IManagerService {
    private final IManagerRepository managerRepo;

    public ManagerService(IManagerRepository managerRepo) {
        this.managerRepo = managerRepo;
    }

    @Override
    @Transactional
    public Manager registerManager(Manager manager) 
            throws ManagerValidationException, ManagerAlreadyExistsException, ManagerOperationException {
        if (!manager.isValid()) {
            log.warn("Invalid manager");
            throw new ManagerValidationException("Invalid manager data");
        }

        try {
            if (managerRepo.existsByLogin(manager.getLogin())) {
                log.warn("Manager with login={} already exists", manager.getLogin());
                throw new ManagerAlreadyExistsException(manager.getLogin());
            }
            manager = managerRepo.save(manager);
            log.info("Saved manager with login={}", manager.getLogin());
            return manager;
        } catch (Exception e) {
            log.error("Failed to register manager", e);
            throw new ManagerOperationException("Failed to register manager: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Manager getManagerByLogin(String login) 
            throws ManagerNotFoundException, ManagerOperationException {
        try {
            if (!managerRepo.existsByLogin(login)) {
                log.warn("Manager with login={} not found", login);
                throw new ManagerAlreadyExistsException(login);
            }
            Manager manager = managerRepo.findByLogin(login).get();
            log.info("Got manager with login={}", login);
            return manager;
        } catch (Exception e) {
            log.error("Failed to get manager", e);
            throw new ManagerOperationException("Failed to get manager: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Manager updateManager(Manager manager) 
            throws ManagerNotFoundException,  ManagerValidationException, ManagerOperationException {
        if (!manager.isValid()) {
            log.warn("Invalid manager");
            throw new ManagerValidationException("Invalid manager data");
        }
        
        try {
            if (!managerRepo.existsByLogin(manager.getLogin())) {
                log.warn("Manager with login={} already exists", manager.getLogin());
                throw new ManagerNotFoundException(manager.getLogin());
            }
            manager = managerRepo.update(manager);
            log.info("Updated manager with login={}", manager.getLogin());
            return manager;
        } catch (Exception e) {
            log.error("Failed to update manager", e);
            throw new ManagerOperationException("Failed to update manager: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteManager(String login) 
            throws ManagerNotFoundException, ManagerOperationException {
        try {
            if (!managerRepo.existsByLogin(login)) {
                log.warn("Manager with login={} already exists", login);
                throw new ManagerNotFoundException(login);
            }
            managerRepo.deleteByLogin(login);
            log.info("Deleted manager with login={}", login);
        } catch (Exception e) {
            log.error("Failed to delete manager", e);
            throw new ManagerOperationException("Failed to delete manager: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean managerExists(String login) 
            throws ManagerOperationException {
        try {
            return managerRepo.existsByLogin(login);
        } catch (Exception e) {
            log.error("Failed to check manager exists with", e);
            throw new ManagerOperationException("Failed to check manager exists: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getManagerProducts(Manager manager) {
        return manager.getProducts();
    }
}