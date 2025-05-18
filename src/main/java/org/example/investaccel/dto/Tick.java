package org.example.investaccel.dto;

import java.math.BigDecimal;

public record Tick(String symbol, BigDecimal price, long timestamp) {}