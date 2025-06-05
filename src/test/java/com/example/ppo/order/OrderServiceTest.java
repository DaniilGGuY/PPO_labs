package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.order.*;
import com.example.ppo.orderproduct.IOrderProductRepository;
import com.example.ppo.orderproduct.ProductQuantityPair;
import com.example.ppo.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepo;

    @Mock
    private IOrderProductRepository opRepo;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private Client testClient;

    @Mock
    private Consultant testConsultant;

    @Mock
    private Product testProduct1;

    @Mock
    private Product testProduct2;

    @Mock
    private Order testOrder;

    @BeforeEach
    void setUp() {
        testClient = Client.builder()
                .id(1L)
                .login("client1")
                .password("password")
                .email("client@test.com")
                .build();

        testConsultant = Consultant.builder()
                .id(1L)
                .login("consultant1")
                .password("password")
                .email("consultant@test.com")
                .build();

        testProduct1 = Product.builder()
                .id(1L)
                .name("Product 1")
                .cost(100.0)
                .build();

        testProduct2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .cost(200.0)
                .build();

        testOrder = Order.builder()
                .id(1L)
                .client(testClient)
                .status(OrderStatus.PENDING)
                .cost(0.0)
                .datetime(LocalDateTime.now())
                .build();
    }

    @Test
    void createOrder_EmptyItems_ThrowsException() {
        assertThrows(OrderValidationException.class,
                () -> orderService.createOrder(testClient, List.of()));
    }

    @Test
    void createOrder_NullItems_ThrowsException() {
        assertThrows(OrderValidationException.class,
                () -> orderService.createOrder(testClient, null));
    }

    @Test
    void addProductToOrder_NullProduct_ThrowsException() {
        Order order = Order.builder().cost(0.0).build();
        ProductQuantityPair op = new ProductQuantityPair(null, 2);
        assertThrows(OrderValidationException.class,
                () -> orderService.addProductToOrder(order, op));
    }

    @Test
    void addProductToOrder_InvalidQuantity_ThrowsException() {
        Order order = Order.builder().cost(0.0).build();
        ProductQuantityPair op = new ProductQuantityPair(testProduct1, 0);
        assertThrows(OrderValidationException.class,
                () -> orderService.addProductToOrder(order, op));
    }

    @Test
    void assignConsultant_Success() throws OrderNotFoundException, OrderOperationException {
        Order pendingOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.PENDING)
                .build();

        when(orderRepo.findById(1L)).thenReturn(Optional.of(pendingOrder));
        when(orderRepo.update(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.assignConsultant(pendingOrder, testConsultant);

        assertEquals(testConsultant, result.getConsultant());
        assertEquals(OrderStatus.IN_PROGRESS, result.getStatus());
        verify(orderRepo).update(result);
    }

    @Test
    void assignConsultant_OrderNotFound_ThrowsException() {
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderOperationException.class,
                () -> orderService.assignConsultant(testOrder, testConsultant));
    }

    @Test
    void assignConsultant_NotPendingStatus_ThrowsException() {
        Order inProgressOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.IN_PROGRESS)
                .build();

        when(orderRepo.findById(1L)).thenReturn(Optional.of(inProgressOrder));

        assertThrows(OrderOperationException.class,
                () -> orderService.assignConsultant(inProgressOrder, testConsultant));
    }

    @Test
    void completeOrder_Success() throws OrderNotFoundException, OrderOperationException {
        Order inProgressOrder = Order.builder()
                .id(1L)
                .consultant(testConsultant)
                .status(OrderStatus.IN_PROGRESS)
                .build();

        when(orderRepo.findById(1L)).thenReturn(Optional.of(inProgressOrder));
        when(orderRepo.update(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.completeOrder(inProgressOrder, testConsultant);

        assertEquals(OrderStatus.COMPLETED, result.getStatus());
        verify(orderRepo).update(result);
    }

    @Test
    void completeOrder_WrongConsultant_ThrowsException() {
        Consultant otherConsultant = Consultant.builder().login("other").build();
        Order inProgressOrder = Order.builder()
                .id(1L)
                .consultant(testConsultant)
                .status(OrderStatus.IN_PROGRESS)
                .build();

        when(orderRepo.findById(1L)).thenReturn(Optional.of(inProgressOrder));

        assertThrows(OrderOperationException.class,
                () -> orderService.completeOrder(inProgressOrder, otherConsultant));
    }

    @Test
    void cancelOrder_Success() throws OrderNotFoundException, OrderOperationException {
        Order inProgressOrder = Order.builder()
                .id(1L)
                .consultant(testConsultant)
                .status(OrderStatus.IN_PROGRESS)
                .build();

        when(orderRepo.findById(1L)).thenReturn(Optional.of(inProgressOrder));
        when(orderRepo.update(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.cancelOrder(inProgressOrder, testConsultant);

        assertEquals(OrderStatus.CANCELLED, result.getStatus());
        verify(orderRepo).update(result);
    }

    @Test
    void calculateTotal_ReturnsCorrectValue() {
        testOrder.setCost(350.0);
        double result = orderService.calculateTotal(testOrder);
        assertEquals(350.0, result, 0.001);
    }

    @Test
    void getOrderById_Found() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(testOrder));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(testOrder, result.get());
    }

    @Test
    void getOrderById_NotFound() {
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByClient_ReturnsOrders() {
        when(orderRepo.findByClient_Id(1L)).thenReturn(List.of(testOrder));

        List<Order> result = orderService.getByClient(testClient);

        assertEquals(1, result.size());
        assertEquals(testOrder, result.get(0));
    }

    @Test
    void findByConsultant_ReturnsOrders() {
        Order completedOrder = Order.builder()
                .id(2L)
                .consultant(testConsultant)
                .status(OrderStatus.COMPLETED)
                .build();

        when(orderRepo.findByConsultant_Id(1L)).thenReturn(List.of(completedOrder));

        List<Order> result = orderService.getByConsultant(testConsultant);

        assertEquals(1, result.size());
        assertEquals(completedOrder, result.get(0));
    }
}