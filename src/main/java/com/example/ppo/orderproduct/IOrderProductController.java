package com.example.ppo.orderproduct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IOrderProductController {
    ResponseEntity<?> createOrderProduct(@RequestParam Long orderId, @RequestParam Long productId, @RequestParam int quantity);

    ResponseEntity<?> updateQuantity(@RequestParam Long orderId, @RequestParam Long productId, @RequestParam int newQuantity);

    ResponseEntity<?> getByOrder(@PathVariable Long orderId);

    ResponseEntity<?> removeOrderProduct(@RequestParam Long orderId, @RequestParam Long productId);
}