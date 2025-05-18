package org.example.investaccel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long buyOrderId;
    private Long sellOrderId;
    private String symbol;
    private BigDecimal price;
    private int quantity;
    private long timestamp;

    public Trade() { }

    public Trade(Long buyOrderId,
                 Long sellOrderId,
                 String symbol,
                 BigDecimal price,
                 int quantity,
                 long timestamp) {
        this.buyOrderId  = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.symbol      = symbol;
        this.price       = price;
        this.quantity    = quantity;
        this.timestamp   = timestamp;
    }

    // If you prefer a simpler constructor when matching:
    public Trade(String symbol, BigDecimal price, int quantity) {
        this(null, null, symbol, price, quantity, System.currentTimeMillis());
    }

    // --- Getters & Setters ---
    public Long getId()                      { return id; }
    public Long getBuyOrderId()              { return buyOrderId; }
    public void setBuyOrderId(Long buyOrderId){ this.buyOrderId = buyOrderId; }
    public Long getSellOrderId()             { return sellOrderId; }
    public void setSellOrderId(Long sellOrderId){ this.sellOrderId = sellOrderId; }
    public String getSymbol()                { return symbol; }
    public void setSymbol(String symbol)     { this.symbol = symbol; }
    public BigDecimal getPrice()             { return price; }
    public void setPrice(BigDecimal price)   { this.price = price; }
    public int getQuantity()                 { return quantity; }
    public void setQuantity(int quantity)    { this.quantity = quantity; }
    public long getTimestamp()               { return timestamp; }
    public void setTimestamp(long timestamp){ this.timestamp = timestamp; }
}