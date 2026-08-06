package org.example.flowmanager.service.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.config.KafkaTopicsConfig;
import org.example.flowmanager.kafka.producer.ProducerEvent;
import org.example.flowmanager.model.entity.OutboxTable;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class UploadProcessor implements EventProcessor {

    private final ProducerEvent producerEvent;
    private final KafkaTopicsConfig kafkaTopicsConfig;

    @Override
    public void process(OutboxTable outboxTable) {
        producerEvent.sendUploadEvent(kafkaTopicsConfig.getFileUpload(),outboxTable.getFileId(), outboxTable.getPayload());
        log.info("Success Event sent to Kafka");
    }
}
