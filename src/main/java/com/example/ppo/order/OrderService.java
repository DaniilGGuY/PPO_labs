package com.example.ppo.order;

import com.example.ppo.order.Order;
import com.example.ppo.order.OrderStatus;
import com.example.ppo.order.IOrderService;
import com.example.ppo.product.Product;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.orderproduct.OrderProduct;
import com.example.ppo.review.Review;
import com.example.ppo.client.Client;
import com.example.ppo.exception.BusinessException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public class OrderService implements IOrderService {
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Order createOrder(Client client, List<OrderProduct> products) throws BusinessException {
        if (client == null) {
            throw new BusinessException("Client cannot be null", "INVALID_CLIENT");
        }
        
        if (products == null || products.isEmpty()) {
            throw new BusinessException("Order cannot be empty", "EMPTY_ORDER");
        }

        Order order = new Order();
        order.setId(idGenerator.getAndIncrement());
        order.setClient(client);
        order.setStatus(OrderStatus.PENDING);
        order.setDatetime(LocalDateTime.now());

        products.forEach(op -> op.setOrder(order));
        
        order.setOrderProducts(products);
        
        client.addOrder(order);
        
        orders.put(order.getId(), order);
       
        return order;
    }

    @Override
    public Order assignConsultant(Order order, Consultant consultant) throws BusinessException {
        if (order == null) {
            throw new BusinessException("Order cannot be null", "INVALID_ORDER");
        }
        
        if (consultant == null) {
            throw new BusinessException("Consultant cannot be null", "INVALID_CONSULTANT");
        }
        
        order.setConsultant(consultant);
        consultant.addOrder(order);
        return order;
    }

    @Override
    public double calculateTotal(Order order) {
        if (order == null || order.getOrderProducts() == null) {
            return 0.0;
        }
        
        return order.getOrderProducts().stream()
            .mapToDouble(op -> op.getProduct().getCost() * op.getQuantity())
            .sum();
    }

    @Override
    public Order getOrderById(long id) {
        return orders.get(id);
    }
}