package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.client.IClientService;
import com.example.ppo.consultant.IConsultantService;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.exception.order.*;
import com.example.ppo.orderproduct.ProductQuantityPair;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing orders")
@Slf4j
public class OrderController implements IOrderController {

    private final IOrderService orderService;
    private final IClientService clientService;
    private final IConsultantService consultantService;

    public OrderController(IOrderService orderService, IClientService clientService, IConsultantService consultantService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.consultantService = consultantService;
    }

    @Operation(summary = "Create new order", description = "Creates a new order with products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid order data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestParam String clientLogin, @RequestBody List<ProductQuantityPair> items) {
        try {
            log.info("POST /api/orders");
            Client client = clientService.getClientByLogin(clientLogin);
            Order order = orderService.createOrder(client, items);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (OrderValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (OrderOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Add product to order", description = "Adds a product to existing order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product added", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product data"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{orderId}/products")
    public ResponseEntity<?> addProductToOrder(@PathVariable Long orderId, @RequestBody ProductQuantityPair product) {
        try {
            log.info("POST /api/orders/{}/products", orderId);
            Order order = orderService.getExistingOrder(orderId);
            orderService.addProductToOrder(order, product);
            return ResponseEntity.ok(orderService.getOrderById(orderId).orElseThrow());
        } catch (OrderValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to add product: " + e.getMessage());
        }
    }

    @Operation(summary = "Assign consultant", description = "Assigns consultant to order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consultant assigned", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid operation"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{orderId}/assign")
    public ResponseEntity<?> assignConsultant(@PathVariable Long orderId, @RequestParam String consultantLogin) {
        try {
            log.info("PUT /api/orders/{}/assign", orderId);
            Order order = orderService.getExistingOrder(orderId);
            Consultant consultant = consultantService.getConsultantByLogin(consultantLogin);
            Order updatedOrder = orderService.assignConsultant(order, consultant);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OrderOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to assign consultant: " + e.getMessage());
        }
    }

    @Operation(summary = "Complete order", description = "Marks order as completed")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order completed", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid operation"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long orderId, @RequestParam String consultantLogin) {
        try {
            log.info("PUT /api/orders/{}/complete", orderId);
            Order order = orderService.getExistingOrder(orderId);
            Consultant consultant = consultantService.getConsultantByLogin(consultantLogin);
            Order updatedOrder = orderService.completeOrder(order, consultant);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OrderOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to complete order: " + e.getMessage());
        }
    }

    @Operation(summary = "Cancel order", description = "Marks order as cancelled")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order cancelled", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Invalid operation"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, @RequestParam String consultantLogin) {
        try {
            log.info("PUT /api/orders/{}/cancel", orderId);
            Order order = orderService.getExistingOrder(orderId);
            Consultant consultant = consultantService.getConsultantByLogin(consultantLogin);
            Order updatedOrder = orderService.cancelOrder(order, consultant);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OrderOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to cancel order: " + e.getMessage());
        }
    }

    @Operation(summary = "Calculate order total", description = "Calculates total cost of order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total calculated", content = @Content(schema = @Schema(implementation = Double.class))),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Failed to cancel order")
    })
    @GetMapping("/{orderId}/total")
    public ResponseEntity<?> calculateTotal(@PathVariable Long orderId) {
        try {
            log.info("GET /api/orders/{}/total", orderId);
            Order order = orderService.getExistingOrder(orderId);
            double total = orderService.calculateTotal(order);
            return ResponseEntity.ok(total);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to calculate order: " + e.getMessage());
        }
    }

    @Operation(summary = "Get order by ID", description = "Returns order by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Failed to find order")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        log.info("GET /api/orders/{}", orderId);
        try {
            Optional<Order> order = orderService.getOrderById(orderId);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found with id: " + orderId);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to cancel order: " + e.getMessage());
        }
    }

    @Operation(summary = "Find orders by client", description = "Returns all orders for specified client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders found", content = @Content(schema = @Schema(implementation = Order[].class))),
        @ApiResponse(responseCode = "500", description = "Failed to find order")
    })
    @GetMapping("/client/{clientLogin}")
    public ResponseEntity<?> findByClient(@PathVariable String clientLogin) {
        try  {
            log.info("POST /api/orders/client/{}", clientLogin);
            Client client = clientService.getClientByLogin(clientLogin);
            return ResponseEntity.ok(orderService.getByClient(client));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to find order: " + e.getMessage());
        }
    }

    @Operation(summary = "Find orders by consultant", description = "Returns all orders for specified consultant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders found", content = @Content(schema = @Schema(implementation = Order[].class))),
        @ApiResponse(responseCode = "500", description = "Failed to find order")
    })
    @GetMapping("/consultant/{consultantLogin}")
    public ResponseEntity<?> findByConsultant(@PathVariable String consultantLogin) {
        try {
            log.info("POST /api/orders/consultant/{}", consultantLogin);
            Consultant consultant = consultantService.getConsultantByLogin(consultantLogin);
            return ResponseEntity.ok(orderService.getByConsultant(consultant));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to find order: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all orders", description = "Returns list of all orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of orders", content = @Content(schema = @Schema(implementation = Order[].class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to find order: " + e.getMessage());
        }
    }
}