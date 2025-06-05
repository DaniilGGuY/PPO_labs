package com.example.ppo.manager;

import com.example.ppo.exception.manager.*;
import com.example.ppo.product.Product;

import java.util.List;

public interface IManagerService {
    Manager registerManager(Manager manager) 
        throws ManagerValidationException, ManagerAlreadyExistsException, ManagerOperationException;
    Manager getManagerByLogin(String login) 
        throws ManagerNotFoundException, ManagerOperationException;
    Manager updateManager(Manager manager) 
        throws ManagerNotFoundException, ManagerValidationException, ManagerOperationException;
    void deleteManager(String login) 
        throws ManagerNotFoundException, ManagerOperationException;
    boolean managerExists(String login)
        throws ManagerOperationException;
    List<Product> getManagerProducts(Manager manager);
}
