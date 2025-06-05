package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.order.*;
import com.example.ppo.orderproduct.IOrderProductRepository;
import com.example.ppo.orderproduct.OrderProduct;
import com.example.ppo.orderproduct.OrderProduct.OrderProductId;
import com.example.ppo.orderproduct.ProductQuantityPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepo;
    private final IOrderProductRepository opRepo;
    public OrderService(IOrderRepository orderRepo, IOrderProductRepository opRepo) {
        this.orderRepo = orderRepo;
        this.opRepo = opRepo;
    }

    @Override
    public Order createOrder(Client client, List<ProductQuantityPair> items) 
            throws OrderValidationException, OrderOperationException {      
        if (items == null || items.isEmpty()) {
            log.warn("Invalid order");
            throw new OrderValidationException("Order must contain at least one product");
        }

        try {
            Order order = Order.builder().client(client).build();
            order = orderRepo.save(order);
            log.info("Created order with id={}", order.getId());
            for (ProductQuantityPair item : items) {
                addProductToOrder(order, item);
            }
            client.addOrder(order);
            log.info("Client {} added order with id={}", client.getLogin(), order.getId());
            log.info("Changed status of order {} to PENDING", order.getId());
            return order;
        } catch (Exception e) {
            log.error("Failed to create order", e);
            throw new OrderOperationException("Failed to create order: " + e.getMessage());
        }
    }

    @Override
    public void addProductToOrder(Order order, ProductQuantityPair pair) 
            throws OrderValidationException, OrderOperationException {
        if (pair.getProduct() == null || pair.getQuantity() == null || pair.getQuantity() <= 0) {
            log.warn("Invalid order part");
            throw new OrderValidationException("Product and quantity must be valid");
        }
        
        try {
            OrderProduct op = new OrderProduct();
            OrderProductId id = new OrderProductId(order.getId(), pair.getProduct().getId());
            op.setId(id);
            op.setOrder(order);
            op.setProduct(pair.getProduct());
            op.setQuantity(pair.getQuantity());
            op = opRepo.save(op);
            order.addOrderProduct(op);
            log.info("Added product with id={} in quantity={}", pair.getProduct().getId(), pair.getQuantity());
            order.setCost(order.getCost() + (op.getProduct().getCost() * op.getQuantity()));
            orderRepo.update(order);
        } catch (Exception e) {
            log.error("Failed to create order", e);
            throw new OrderOperationException("Failed to add item in order: " + e.getMessage());
        }
    }

    @Override
    public Order assignConsultant(Order order, Consultant consultant) 
            throws OrderNotFoundException, OrderOperationException {        
        try {
            Order existingOrder = getExistingOrder(order.getId());
            if (existingOrder.getStatus() != OrderStatus.PENDING) {
                log.warn("Invalid order status");
                throw new OrderValidationException("Cannot assign consultant to order with status " + existingOrder.getStatus());
            }
            existingOrder.setConsultant(consultant);
            existingOrder.setStatus(OrderStatus.IN_PROGRESS);
            consultant.addOrder(existingOrder);
            existingOrder = orderRepo.update(existingOrder);
            log.info("Assigned consultant {} to order with id={}", consultant.getLogin(), existingOrder.getId());
            log.info("Changed status of order {} to IN_PROGRESS", existingOrder.getId());
            return existingOrder;
        } catch (Exception e) {
            throw new OrderOperationException("Failed to assign consultant: " + e.getMessage());
        }
    }

    @Override
    public Order completeOrder(Order order, Consultant consultant) 
            throws OrderNotFoundException, OrderOperationException {
        try {
            Order existingOrder = getExistingOrder(order.getId());
            if (existingOrder.getStatus() != OrderStatus.IN_PROGRESS) {
                log.warn("Invalid order status");
                throw new OrderValidationException("Cannot complete order with status " + existingOrder.getStatus());
            }
            existingOrder.setStatus(OrderStatus.COMPLETED);
            existingOrder = orderRepo.update(existingOrder);
            log.info("Changed status of order {} to COMPLETED", existingOrder.getId());
            return existingOrder;
        } catch (Exception e) {
            throw new OrderOperationException("Failed to complete order: " + e.getMessage());
        }
    }

    @Override
    public Order cancelOrder(Order order, Consultant consultant) 
            throws OrderNotFoundException, OrderOperationException {
        try {
            Order existingOrder = getExistingOrder(order.getId());
            if (existingOrder.getStatus() != OrderStatus.IN_PROGRESS) {
                log.warn("Invalid order status");
                throw new OrderValidationException("Cannot cancel order with status " + existingOrder.getStatus());
            } 
            existingOrder.setStatus(OrderStatus.CANCELLED);
            existingOrder = orderRepo.update(existingOrder);
            log.info("Changed status of order {} to CANCELED", existingOrder.getId());
            return existingOrder;
        } catch (Exception e) {
            throw new OrderOperationException("Failed to cancel order: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public double calculateTotal(Order order) {
        return order.getCost();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long id) 
            throws OrderOperationException {
        try {
            return orderRepo.findById(id);
        } catch (Exception e) {
            throw new OrderOperationException("Failed to find order: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByClient(Client client) 
            throws OrderOperationException {
        try {
            return orderRepo.findByClient_Id(client.getId());
        } catch (Exception e) {
            throw new OrderOperationException("Failed to find order: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByConsultant(Consultant consultant) 
            throws OrderOperationException {
        try {
            return orderRepo.findByConsultant_Id(consultant.getId());
        } catch (Exception e) {
            throw new OrderOperationException("Failed to find order: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders()
            throws OrderOperationException {
        try {
            return orderRepo.findAll();
        } catch (Exception e) {
            throw new OrderOperationException("Failed to find orders: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Order getExistingOrder(Long id) 
            throws OrderNotFoundException, OrderOperationException {
        try {
            return orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        } catch (Exception e) {
            throw new OrderOperationException("Failed to find order: " + e.getMessage());
        }
    }
}