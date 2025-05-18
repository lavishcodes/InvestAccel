package org.example.investaccel.dto;

import java.math.BigDecimal;

public record OrderRequest(
        String symbol,
        String side,
        BigDecimal price,
        int quantity
) {}