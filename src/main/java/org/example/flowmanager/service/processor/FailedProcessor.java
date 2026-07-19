package org.example.flowmanager.service.processor;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.kafka.KafkaTopics;
import org.example.flowmanager.kafka.producer.ProducerEvent;
import org.example.flowmanager.model.entity.OutboxTable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FailedProcessor implements EventProcessor{

    private final ProducerEvent producerEvent;
    private final KafkaTopics kafkaTopics;

    @Override
    public void process(OutboxTable outboxTable) {
        producerEvent.sendFailedEvent(kafkaTopics.getFileFailed(), outboxTable.getUuid(), outboxTable.getPayload());
    }
}
