package org.example.flowmanager.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.dto.FileUploadDto;
import org.example.flowmanager.model.entity.InboxMessage;
import org.example.flowmanager.repository.InboxRepository;
import org.example.flowmanager.service.FlowManager;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Transactional
public class Consumer {

    private final InboxRepository inboxRepository;
    private final FlowManager flowManager;
    @RetryableTopic(
            attempts = "4",
            backOff = @BackOff(delay = 5000)
    )
    @KafkaListener(topics = "${spring.kafka.topics.file-update}")
    public void processConsumer(FileUploadDto event) {
        boolean ExistId = inboxRepository.existById(event.getUuid());
        if (ExistId) {
            return;
        }

        InboxMessage inboxMessage = new InboxMessage();
        inboxMessage.setFileId(event.getFileId());
        inboxMessage.setPayload(event.getPayload());

        flowManager.processSendFile(inboxMessage);
    }
}
