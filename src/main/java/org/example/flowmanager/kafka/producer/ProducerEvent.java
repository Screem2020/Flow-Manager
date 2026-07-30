package org.example.flowmanager.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProducerEvent {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendUploadEvent(String topic, UUID uuid, String payload) {
        kafkaTemplate.send(topic, uuid.toString(), payload);
    }

    public void sendFailedEvent(String topic, UUID uuid, String payload) {
        kafkaTemplate.send(topic, uuid.toString(), payload);
    }

    public void sendDltEvent(String topic, UUID uuid, String payload) {
        kafkaTemplate.send(topic, uuid.toString(), payload);
    }
}
