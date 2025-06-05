package com.example.ppo.integra;

import com.example.ppo.client.Client;
import com.example.ppo.client.ClientService;
import com.example.ppo.order.Order;
import com.example.ppo.order.OrderService;
import com.example.ppo.orderproduct.OrderProduct;
import com.example.ppo.orderproduct.OrderProductService;
import com.example.ppo.orderproduct.ProductQuantityPair;
import com.example.ppo.product.Product;
import com.example.ppo.product.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderProductIntegrationTest {
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderProductService orderProductService;

    @Test
    void shouldUpdateOrderTotalWhenChangingProducts() throws Exception {
        Client client = clientService.registerClient(
            Client.builder()
                .login("test_client")
                .password("password123")
                .tel("+71234567890")
                .email("client@test.com")
                .company("Test Company")
                .build());

        Product product1 = productService.createProduct(
            Product.builder().name("Product 1").cost(100.0).build());
        Product product2 = productService.createProduct(
            Product.builder().name("Product 2").cost(200.0).build());

        Order order = orderService.createOrder(client, List.of(new ProductQuantityPair(product1, 2)));

        assertEquals(200.0, order.getCost());

        ProductQuantityPair op = new ProductQuantityPair(product2, 1);
        
        orderService.addProductToOrder(order, op);
        assertEquals(400.0, order.getCost());

        orderProductService.updateQuantity(
            new OrderProduct.OrderProductId(order.getId(), product1.getId()), 
            3);
        
        order = orderService.getOrderById(order.getId()).get();
        assertEquals(500.0, order.getCost());
    }
}