package com.example.ppo.order;

import com.example.ppo.orderproduct.ProductQuantityPair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IOrderController {
    ResponseEntity<?> createOrder(@RequestParam String clientLogin, @RequestBody List<ProductQuantityPair> items);

    ResponseEntity<?> addProductToOrder(@PathVariable Long orderId, @RequestBody ProductQuantityPair product);

    ResponseEntity<?> assignConsultant(@PathVariable Long orderId, @RequestParam String consultantLogin);

    ResponseEntity<?> completeOrder(@PathVariable Long orderId, @RequestParam String consultantLogin);

    ResponseEntity<?> cancelOrder(@PathVariable Long orderId, @RequestParam String consultantLogin);

    ResponseEntity<?> calculateTotal(@PathVariable Long orderId);

    ResponseEntity<?> getOrderById(@PathVariable Long orderId);

    ResponseEntity<?> findByClient(@PathVariable String clientLogin);

    ResponseEntity<?> findByConsultant(@PathVariable String consultantLogin);

    ResponseEntity<?> findAll();
}