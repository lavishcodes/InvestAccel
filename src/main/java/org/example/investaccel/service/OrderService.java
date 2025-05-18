package org.example.investaccel.service;

import org.example.investaccel.dto.OrderRequest;
import org.example.investaccel.entity.Order;
import org.example.investaccel.entity.Trade;
import org.example.investaccel.entity.Side;
import org.example.investaccel.entity.Status;
import org.example.investaccel.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final MatchingEngine engine;

    public OrderService(OrderRepository orderRepo, MatchingEngine engine) {
        this.orderRepo = orderRepo;
        this.engine = engine;
    }

    @Transactional
    public List<Trade> placeOrder(Long userId, OrderRequest req) {
        Order ord = new Order(
                userId,
                req.symbol(),
                Side.valueOf(req.side().toUpperCase()),
                req.price(),
                req.quantity()
        );
        ord.setStatus(Status.PENDING);
        orderRepo.save(ord);

        List<Trade> trades = engine.match(ord);
        orderRepo.save(ord);
        return trades;
    }
}
