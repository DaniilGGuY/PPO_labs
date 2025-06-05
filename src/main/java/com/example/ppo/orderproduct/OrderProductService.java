package com.example.ppo.orderproduct;

import com.example.ppo.exception.orderproduct.*;
import com.example.ppo.order.IOrderRepository;
import com.example.ppo.order.Order;
import com.example.ppo.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@Transactional
@Slf4j
public class OrderProductService implements IOrderProductService {

    private final IOrderProductRepository opRepo;
    private final IOrderRepository orderRepo;
    public OrderProductService(IOrderProductRepository opRepo, IOrderRepository orderRepo) {
        this.opRepo = opRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public OrderProduct createOrderProduct(Order order, Product product, int quantity)
            throws OrderProductValidationException, OrderProductOperationException {
        OrderProduct orderProduct = OrderProduct.builder()
            .id(new OrderProduct.OrderProductId(order.getId(), product.getId()))
            .order(order)
            .product(product)
            .quantity(quantity)
            .build();
        if (!orderProduct.isValid()) {
            log.warn("Invalid order-product");
            throw new OrderProductValidationException("Invalid OrderProduct data");
        }

        try {
            orderProduct = opRepo.save(orderProduct);
            log.info("Saved order-product, order {}, product {} in quantity={}", order.getId(), product.getId(), quantity);
            return orderProduct;
        } catch (Exception e) {
            log.error("Failed to save order-product", e);
            throw new OrderProductOperationException("Failed to create OrderProduct: " + e.getMessage());
        }
    }

    @Override
    public OrderProduct updateQuantity(OrderProduct.OrderProductId id, int newQuantity)
            throws OrderProductNotFoundException, OrderProductValidationException, OrderProductOperationException {
        if (newQuantity <= 0) {
            log.warn("Invalid order-product quantity");
            throw new OrderProductValidationException("quantity", "Quantity must be positive");
        }
        try {
            OrderProduct orderProduct = opRepo.findById(id)
                    .orElseThrow(() -> new OrderProductNotFoundException(id.getOrderId(), id.getProductId()));
            Order order = orderProduct.getOrder();
            double costDifference = orderProduct.getProduct().getCost() * (newQuantity - orderProduct.getQuantity());
            orderProduct.setQuantity(newQuantity);
            orderProduct = opRepo.save(orderProduct);
            order.setCost(order.getCost() + costDifference);
            orderRepo.save(order);
            log.info("Updated order-product, order {}, product {} in quantity={}", 
                    orderProduct.getOrder().getId(), orderProduct.getProduct().getId(), orderProduct.getQuantity());
            return orderProduct;
        } catch (Exception e) {
            log.error("Failed to update order-product", e);
            throw new OrderProductOperationException("Failed to update quantity: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderProduct> getByOrder(Order order) 
            throws OrderProductOperationException {
        try {
            return opRepo.findByOrderId(order.getId());
        } catch (Exception e) {
            log.error("Failed to find order-product", e);
            throw new OrderProductOperationException("Failed to find order-product: " + e.getMessage());
        }
    }

    @Override
    public void removeOrderProduct(OrderProduct op) throws OrderProductNotFoundException, OrderProductOperationException {
        try {
            OrderProduct existOp = opRepo.findById(op.getId())
                    .orElseThrow(() -> new OrderProductNotFoundException(op.getId().getOrderId(), op.getId().getProductId()));
            opRepo.delete(existOp);
            log.info("Deleted order-product, order {}, product {} in quantity={}", op.getOrder().getId(), op.getProduct().getId(), op.getQuantity());
        } catch (Exception e) {
            log.error("Failed to delete order-product", e);
            throw new OrderProductOperationException("Failed to delete OrderProduct: " + e.getMessage());
        }
    }
}