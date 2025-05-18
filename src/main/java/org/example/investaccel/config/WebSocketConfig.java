package org.example.investaccel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")               // client connects here
                .setAllowedOriginPatterns("*")    // adjust for prod
                .withSockJS();                    // fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // messages from server to client prefixed with /topic
        registry.enableSimpleBroker("/topic", "/queue");
        // messages from client to server prefixed with /app
        registry.setApplicationDestinationPrefixes("/app");
    }
}
