package org.example.investaccel.service;

import org.example.investaccel.entity.Order;
import org.example.investaccel.entity.Trade;
import org.example.investaccel.entity.Side;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MatchingEngine {

    private final PriorityQueue<Order> buyBook = new PriorityQueue<>(Comparator.comparing(Order::getPrice).reversed());
    private final PriorityQueue<Order> sellBook = new PriorityQueue<>(Comparator.comparing(Order::getPrice));
    private final List<Trade> trades = new ArrayList<>();

    public List<Trade> match(Order incoming) {
        PriorityQueue<Order> oppositeBook = incoming.getSide() == Side.BUY ? sellBook : buyBook;
        while (!oppositeBook.isEmpty() && incoming.getQuantity() > 0) {
            Order resting = oppositeBook.peek();
            boolean priceMatch = incoming.getSide() == Side.BUY
                    ? incoming.getPrice().compareTo(resting.getPrice()) >= 0
                    : incoming.getPrice().compareTo(resting.getPrice()) <= 0;
            if (!priceMatch) break;

            int matchQty = Math.min(incoming.getQuantity(), resting.getQuantity());
            BigDecimal tradePrice = resting.getPrice();

            Trade trade = new Trade(
                    incoming.getSide() == Side.BUY ? incoming.getId() : resting.getId(),
                    incoming.getSide() == Side.SELL ? incoming.getId() : resting.getId(),
                    incoming.getSymbol(),
                    tradePrice,
                    incoming.getSide().name(),
                    matchQty,
                    System.currentTimeMillis()
            );
            trades.add(trade);

            incoming.setQuantity(incoming.getQuantity() - matchQty);
            resting.setQuantity(resting.getQuantity() - matchQty);

            if (resting.getQuantity() == 0) {
                oppositeBook.poll();
            }
        }

        if (incoming.getQuantity() > 0) {
            if (incoming.getSide() == Side.BUY) buyBook.add(incoming);
            else sellBook.add(incoming);
        }
        return trades;
    }
}
