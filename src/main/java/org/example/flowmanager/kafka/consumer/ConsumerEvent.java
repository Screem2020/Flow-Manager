package org.example.flowmanager.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.exception.InboxProcessingException;
import org.example.flowmanager.model.dto.FileUpdateDto;
import org.example.flowmanager.model.entity.InboxMessage;
import org.example.flowmanager.repository.InboxRepository;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumerEvent {

    private final InboxRepository inboxRepository;
    private final ObjectMapper objectMapper;

    @RetryableTopic(
            attempts = "4",
            backOff = @BackOff(delay = 5000)
    )
    @KafkaListener(topics = "${spring.kafka.topics.file-update}")
    public void processConsumer(String event) {
        log.info("Received file update event: {}", event);
        try {
            FileUpdateDto fileUpdateDto = objectMapper.readValue(event, FileUpdateDto.class);
            log.info("Event already processed: {}", fileUpdateDto.getFileId());
            if (inboxRepository.existsByFileId(fileUpdateDto.getFileId())) {
                log.info("Event {} already processed. Skip.", fileUpdateDto.getFileId());
                return;
            }
            saveInboxMessage(fileUpdateDto);
        } catch (Exception e) {
            log.error("Error while processing file update event: {}", event, e);
            throw new InboxProcessingException("File save failed");
        }
    }
    public void saveInboxMessage(FileUpdateDto fileUpdateDto) {
        try {
            InboxMessage inboxMessage = new InboxMessage();
            inboxMessage.setFileId(fileUpdateDto.getFileId());
            inboxMessage.setPayload(fileUpdateDto.getPayload());
            log.info("Saving inbox message: {}",  fileUpdateDto.getPayload());

            inboxRepository.save(inboxMessage);
        } catch (Exception ex) {
            log.info("Error while saving inbox message", ex);
            throw new InboxProcessingException("File save failed");
        }
    }
}
