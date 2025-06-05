package com.example.ppo.orderproduct;

import com.example.ppo.order.IOrderService;
import com.example.ppo.exception.orderproduct.*;
import com.example.ppo.order.Order;
import com.example.ppo.product.IProductService;
import com.example.ppo.product.Product;
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

@RestController
@RequestMapping("/api/order-products")
@Tag(name = "Order Product Management", description = "APIs for managing order products")
@Slf4j
public class OrderProductController implements IOrderProductController {

    private final IOrderProductService orderProductService;
    private final IProductService productService;
    private final IOrderService orderService;

    public OrderProductController(IOrderProductService orderProductService, IProductService productService, IOrderService orderService) {
        this.orderProductService = orderProductService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Operation(summary = "Create order product", description = "Adds a product to an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order product created", content = @Content(schema = @Schema(implementation = OrderProduct.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createOrderProduct(@RequestParam Long orderId, @RequestParam Long productId, @RequestParam int quantity) { 
        try {
            log.info("POST /api/order-products");
            Order order = orderService.getExistingOrder(orderId);
            Product product = productService.getExistingProduct(productId);
            OrderProduct orderProduct = orderProductService.createOrderProduct(order, product, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderProduct);
        } catch (OrderProductValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (OrderProductOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Update quantity", description = "Updates product quantity in order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quantity updated", content = @Content(schema = @Schema(implementation = OrderProduct.class))),
        @ApiResponse(responseCode = "400", description = "Invalid quantity"),
        @ApiResponse(responseCode = "404", description = "Order product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/quantity")
    public ResponseEntity<?> updateQuantity(@RequestParam Long orderId, @RequestParam Long productId, @RequestParam int newQuantity) { 
        try {
            log.info("PUT /api/order-products/quantity");
            OrderProduct.OrderProductId id = new OrderProduct.OrderProductId(orderId, productId);
            OrderProduct updated = orderProductService.updateQuantity(id, newQuantity);
            return ResponseEntity.ok(updated);
        } catch (OrderProductValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (OrderProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OrderProductOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get by order", description = "Get all products for specified order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order products found", content = @Content(schema = @Schema(implementation = OrderProduct[].class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getByOrder(@PathVariable Long orderId) {
        try {
            log.info("GET /api/order-products/order/{}", orderId);
            Order order = orderService.getExistingOrder(orderId);
            List<OrderProduct> orderProducts = orderProductService.getByOrder(order);
            return ResponseEntity.ok(orderProducts);
        } catch (OrderProductOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Remove order product", description = "Removes product from order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Order product removed"),
        @ApiResponse(responseCode = "404", description = "Order product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping
    public ResponseEntity<?> removeOrderProduct(@RequestParam Long orderId, @RequestParam Long productId) {
        try {
            log.info("DELETE /api/order-products");
            OrderProduct op = new OrderProduct();
            op.setId(new OrderProduct.OrderProductId(orderId, productId));
            orderProductService.removeOrderProduct(op);
            return ResponseEntity.noContent().build();
        } catch (OrderProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OrderProductOperationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}