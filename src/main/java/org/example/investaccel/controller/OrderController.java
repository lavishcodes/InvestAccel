package org.example.investaccel.controller;

import org.example.investaccel.dto.OrderRequest;
import org.example.investaccel.entity.Trade;
import org.example.investaccel.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<List<Trade>> postOrder(
            Principal principal,
            @RequestBody OrderRequest req) {
        Long userId = Long.valueOf(principal.getName());
        List<Trade> trades = orderService.placeOrder(userId, req);
        return ResponseEntity.ok(trades);
    }
}