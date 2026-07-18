package org.example.flowmanager.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendUpdate(String topic, UUID uuid, String path) {
        kafkaTemplate.send(topic, uuid.toString(), path);
    }
}
