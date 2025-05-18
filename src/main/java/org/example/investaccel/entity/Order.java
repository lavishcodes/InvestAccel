package org.example.investaccel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String symbol;

    @Enumerated(EnumType.STRING)
    private Side side;

    private BigDecimal price;
    private int quantity;
    private int remaining = 0;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Order() { }

    public Order(Long userId, String symbol, Side side, BigDecimal price, int quantity) {
        this.userId    = userId;
        this.symbol    = symbol;
        this.side      = side;
        this.price     = price;
        this.quantity  = quantity;
        this.remaining = quantity;
        this.status    = Status.PENDING;
    }

    public Long getId()                     { return id; }
    public Long getUserId()                 { return userId; }
    public void setUserId(Long userId)      { this.userId = userId; }
    public String getSymbol()               { return symbol; }
    public void setSymbol(String symbol)    { this.symbol = symbol; }
    public Side getSide()                   { return side; }
    public void setSide(Side side)          { this.side = side; }
    public BigDecimal getPrice()            { return price; }
    public void setPrice(BigDecimal price)  { this.price = price; }
    public int getQuantity()                { return quantity; }
    public void setQuantity(int quantity)   { this.quantity = quantity; }
    public int getRemaining()               { return remaining; }
    public void setRemaining(int remaining) { this.remaining = remaining; }
    public Status getStatus()               { return status; }
    public void setStatus(Status status)    { this.status = status; }
}