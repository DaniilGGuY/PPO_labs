package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.BusinessException;
import com.example.ppo.product.Product;
import com.example.ppo.orderproduct.OrderProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private OrderService orderService;

    @Mock(lenient = true)
    private Client mockClient;

    @Mock(lenient = true)
    private Consultant mockConsultant;

    @Mock(lenient = true)
    private Product mockProduct1;

    @Mock(lenient = true)
    private Product mockProduct2;

    @Mock(lenient = true)
    private OrderProduct mockOrderProduct1;

    @Mock(lenient = true)
    private OrderProduct mockOrderProduct2;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();

        when(mockClient.getId()).thenReturn(1L);
        when(mockConsultant.getId()).thenReturn(1L);
    }

    private List<OrderProduct> createMockOrderProducts() {
        when(mockProduct1.getId()).thenReturn(1L);
        when(mockProduct1.getCost()).thenReturn(100.0);
        when(mockOrderProduct1.getProduct()).thenReturn(mockProduct1);
        when(mockOrderProduct1.getQuantity()).thenReturn(2);

        when(mockProduct2.getId()).thenReturn(2L);
        when(mockProduct2.getCost()).thenReturn(200.0);
        when(mockOrderProduct2.getProduct()).thenReturn(mockProduct2);
        when(mockOrderProduct2.getQuantity()).thenReturn(1);

        return List.of(mockOrderProduct1, mockOrderProduct2);
    }

    @Test
    void createOrder_Success() throws BusinessException {
        List<OrderProduct> mockProducts = createMockOrderProducts();

        Order order = orderService.createOrder(mockClient, mockProducts);

        assertNotNull(order);
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(mockClient, order.getClient());
        assertEquals(2, order.getOrderProducts().size());
        assertTrue(order.getId() > 0);

        verify(mockOrderProduct1).setOrder(order);
        verify(mockOrderProduct2).setOrder(order);
    }

    @Test
    void createOrder_NullClient_ThrowsException() {
        assertThrows(BusinessException.class,
            () -> orderService.createOrder(null, createMockOrderProducts()));
    }

    @Test
    void assignConsultant_Success() throws BusinessException {
        Order order = orderService.createOrder(mockClient, createMockOrderProducts());
        
        Order updated = orderService.assignConsultant(order, mockConsultant);

        assertEquals(mockConsultant, updated.getConsultant());
        verify(mockConsultant).addOrder(updated);
    }

    @Test
    void calculateTotal_ReturnsCorrectSum() throws BusinessException {
        List<OrderProduct> mockProducts = createMockOrderProducts();
        
        Order order = orderService.createOrder(mockClient, mockProducts);
        
        double total = orderService.calculateTotal(order);

        assertEquals(400.0, total);
        verify(mockProduct1, times(1)).getCost();
        verify(mockProduct2).getCost();
    }

    @Test
    void getOrderById_ReturnsCorrectOrder() throws BusinessException {
        List<OrderProduct> mockProducts = createMockOrderProducts();
        
        Order created = orderService.createOrder(mockClient, mockProducts);
        
        Order found = orderService.getOrderById(created.getId());

        assertEquals(created, found);
    }

    @Test
    void createOrder_ShouldSetCurrentDateTime() throws BusinessException {
        List<OrderProduct> mockProducts = createMockOrderProducts();
        
        Order order = orderService.createOrder(mockClient, mockProducts);

        assertNotNull(order.getDatetime());
        
        LocalDateTime now = LocalDateTime.now();
        
        assertTrue(order.getDatetime().isBefore(now.plusSeconds(1)));
        assertTrue(order.getDatetime().isAfter(now.minusMinutes(1)));
    }

    @Test
    void createOrder_WithEmptyProductsList_ShouldThrowException() {
         assertThrows(BusinessException.class,
            () -> orderService.createOrder(mockClient, List.of()));
     }

     @Test
     void calculateTotal_WithZeroQuantity_ShouldReturnZero() throws BusinessException {
         OrderProduct zeroQuantityProduct = mock(OrderProduct.class);
         when(zeroQuantityProduct.getQuantity()).thenReturn(0);
         when(zeroQuantityProduct.getProduct()).thenReturn(mockProduct1);

         Order order = orderService.createOrder(mockClient, List.of(zeroQuantityProduct));
         assertEquals(0.0, orderService.calculateTotal(order));
     }

     @Test
     void getOrderById_WithNonExistingId_ShouldReturnNull() {
         assertNull(orderService.getOrderById(999L));
     }

     @Test
     void assignConsultant_ToNullOrder_ShouldThrowException() {
         assertThrows(BusinessException.class,
             () -> orderService.assignConsultant(null, mockConsultant));
     }

     @Test
     void calculateTotal_ForNullOrder_ShouldReturnZero() {
         assertEquals(0.0, orderService.calculateTotal(null));
     }

     @Test
     void createOrder_ShouldAddOrderToClient() throws BusinessException {
         List<OrderProduct> mockProducts = createMockOrderProducts();
         
         Order order = orderService.createOrder(mockClient, mockProducts);

         verify(mockClient).addOrder(order);
     }

     @Test
     void assignConsultant_ShouldAddOrderToConsultant() throws BusinessException {
         List<OrderProduct> mockProducts = createMockOrderProducts();
         
         Order order = orderService.createOrder(mockClient, mockProducts);
         
         orderService.assignConsultant(order, mockConsultant);

         verify(mockConsultant).addOrder(order);
     }
}