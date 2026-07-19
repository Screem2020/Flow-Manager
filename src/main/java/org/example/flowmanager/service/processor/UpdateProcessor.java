package org.example.flowmanager.service.processor;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.kafka.KafkaTopics;
import org.example.flowmanager.kafka.producer.ProducerEvent;
import org.example.flowmanager.model.entity.OutboxTable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProcessor implements EventProcessor {

    private final ProducerEvent producerEvent;
    private final KafkaTopics kafkaTopics;

    @Override
    public void process(OutboxTable outboxTable) {
        producerEvent.sendUpdateEvent(kafkaTopics.getUpdateTopic(),outboxTable.getUuid(), outboxTable.getPayload());
    }
}
