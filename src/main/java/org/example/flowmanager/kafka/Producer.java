package org.example.flowmanager.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class Producer {
    private final KafkaTemplate<UUID, String> kafkaTemplate;

    public void sendUpload(String topic, UUID uuid, String message) {
        kafkaTemplate.send(topic, uuid, message);
    }
}
