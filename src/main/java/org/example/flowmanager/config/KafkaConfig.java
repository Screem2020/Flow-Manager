package org.example.flowmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.UUID;

@Configuration
public class KafkaConfig {
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<UUID, String> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        FixedBackOff fixedBackOff = new FixedBackOff(2000L, 3);
        return new DefaultErrorHandler(recoverer, fixedBackOff);
    }
}
