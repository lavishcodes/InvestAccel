package org.example.investaccel.service;
import org.example.investaccel.dto.Tick;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

@Service
public class MarketDataService {
    private final SimpMessagingTemplate template;
    private final Random random = new Random();

    public MarketDataService(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedRate = 500)
    public void publishTick() {
        Tick tick = new Tick(
                "AAPL",
                BigDecimal.valueOf(100 + random.nextDouble() * 10),
                Instant.now().toEpochMilli()
        );
        template.convertAndSend("/topic/quotes", tick);
    }
}