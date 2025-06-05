package com.example.ppo.integra;

import com.example.ppo.client.*;
import com.example.ppo.client.jpa.JpaClientRepo;
import com.example.ppo.exception.order.OrderValidationException;
import com.example.ppo.order.*;
import com.example.ppo.order.jpa.JpaOrderRepo;
import com.example.ppo.product.*;
import jakarta.transaction.Transactional;
import com.example.ppo.orderproduct.OrderProduct;
import com.example.ppo.orderproduct.ProductQuantityPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClientOrderIntegrationTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private JpaOrderRepo orderRepo;

    @Autowired
    private JpaClientRepo clientRepo;

    private Client testClient;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testClient = Client.builder()
                .login("test_client_" + System.currentTimeMillis())
                .password("password123")
                .tel("+71234567890")
                .email("test" + System.currentTimeMillis() + "@example.com")
                .company("Test Company")
                .build();

        testProduct = Product.builder()
                .name("Test Product")
                .cost(100.0)
                .about("Test Description")
                .build();
    }

    @Test
    void shouldCreateOrderWithProducts() throws OrderValidationException {
        // Подготовка данных
        Client savedClient = clientService.registerClient(testClient);
        assertNotNull(savedClient);
        assertEquals(savedClient.getTel(), "+71234567890");
        Optional<Client> found = clientRepo.findByLogin(savedClient.getLogin());
        assertTrue(found.isPresent());
        Product savedProduct = productService.createProduct(testProduct);

        // Создание заказа
        Order order = orderService.createOrder(
            savedClient, 
            List.of(new ProductQuantityPair(savedProduct, 2))
        );

        // Проверки
        assertNotNull(order.getId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(200.0, order.getCost(), 0.001);
        assertEquals(1, order.getOrderProducts().size());
        
        // Проверка связи Order-OrderProduct
        OrderProduct orderProduct = order.getOrderProducts().get(0);
        assertNotNull(orderProduct.getOrder());
        assertEquals(order.getId(), orderProduct.getOrder().getId());
        assertEquals(savedProduct.getId(), orderProduct.getProduct().getId());
    }

    @Test
    void shouldCalculateTotalCorrectly() throws OrderValidationException {
        Client savedClient = clientService.registerClient(testClient);
        Product savedProduct = productService.createProduct(testProduct);

        Order order = orderService.createOrder(
            savedClient,
            List.of(new ProductQuantityPair(savedProduct, 3))
        );

        assertEquals(300.0, orderService.calculateTotal(order), 0.001);
    }

    @Test
    void shouldFailWhenCreatingEmptyOrder() {
        Client savedClient = clientService.registerClient(testClient);

        assertThrows(OrderValidationException.class, 
            () -> orderService.createOrder(savedClient, List.of())
        );
    }

    @Test
    void shouldPersistOrderWithProducts() throws OrderValidationException {
        Client savedClient = clientService.registerClient(testClient);
        Product savedProduct = productService.createProduct(testProduct);

        Order order = orderService.createOrder(
            savedClient,
            List.of(new ProductQuantityPair(savedProduct, 1))
        );

        Optional<Order> foundOrder = orderRepo.findById(order.getId());
        assertTrue(foundOrder.isPresent());
    }
}