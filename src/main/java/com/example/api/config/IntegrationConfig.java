package com.example.api.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {
    @Bean
    public MessageChannel approvalChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageChannel documentChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageChannel emailChannel() {
        return new DirectChannel();
    }
}


