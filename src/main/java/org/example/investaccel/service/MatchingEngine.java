package org.example.investaccel.service;

import org.example.investaccel.entity.Order;
import org.example.investaccel.entity.Trade;
import org.example.investaccel.entity.Side;
import  org.example.investaccel.entity.Status;
import org.example.investaccel.service.OrderService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class MatchingEngine {
    private final NavigableMap<BigDecimal, Queue<Order>> bids = new TreeMap<>(Comparator.reverseOrder());
    private final NavigableMap<BigDecimal, Queue<Order>> asks = new TreeMap<>();

    public List<Trade> match(Order incoming) {
        List<Trade> trades = new ArrayList<>();
        // Use Order.Side rather than Side
        var book = incoming.getSide() == Side.BUY ? asks : bids;

        while (incoming.getRemaining() > 0 && !book.isEmpty()) {
            BigDecimal bestPrice = book.firstKey();
            boolean priceMismatch = incoming.getSide() == Side.BUY
                    ? bestPrice.compareTo(incoming.getPrice()) > 0
                    : bestPrice.compareTo(incoming.getPrice()) < 0;
            if (priceMismatch) break;

            Order resting = book.get(bestPrice).peek();
            int matchQty = Math.min(incoming.getRemaining(), resting.getRemaining());

            // Create trade record (matches your Trade constructor)
            Trade trade = new Trade(incoming.getSymbol(), bestPrice, matchQty);
            trades.add(trade);

            incoming.setRemaining(incoming.getRemaining() - matchQty);
            resting.setRemaining(resting.getRemaining() - matchQty);
            if (resting.getRemaining() == 0) {
                book.get(bestPrice).poll();
            }
        }

        // If any quantity remains, add incoming to the order book
        if (incoming.getRemaining() > 0) {
            var target = incoming.getSide() == Side.BUY ? bids : asks;
            target.computeIfAbsent(incoming.getPrice(), p -> new LinkedList<>()).add(incoming);
        }

        return trades;
    }
}
