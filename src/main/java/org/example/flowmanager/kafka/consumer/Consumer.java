//package org.example.flowmanager.kafka.consumer;
//
//import lombok.RequiredArgsConstructor;
//import org.example.flowmanager.model.entity.InboxMessage;
//import org.example.flowmanager.model.dto.SendConversionDto;
//import org.example.flowmanager.repository.InboxRepository;
//import org.springframework.kafka.annotation.BackOff;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.RetryableTopic;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class Consumer {
//
//    private final InboxRepository inboxRepository;
//    @RetryableTopic(
//            attempts = "4",
//            backOff = @BackOff(delay = 5000)
//    )
//    @KafkaListener(topics = "${spring.kafka.topics.file-update}")
//    public void processConsumer(SendConversionDto event) {
//        boolean ExistId = inboxRepository.existById(event.getUuid());
//        if (ExistId) {
//            return;
//        }
//
//        InboxMessage inboxMessage = new InboxMessage();
//        inboxMessage.setUuid(event.getUuid());
//        inboxMessage.setBucket(event.getBucket());
//        inboxMessage.setKeyFile(event.getKeyFile());
//
//
//
//
//    }
//
//}
