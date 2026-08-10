package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.example.flowmanager.model.entity.InboxMessage;
import org.example.flowmanager.repository.InboxRepository;
import org.example.flowmanager.repository.SubscriptionRedisRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Service
@Slf4j
public class UploadSubscriptionMessage {
    private final ObjectMapper objectMapper;
    private final InboxRepository inboxRepository;
    private final SubscriptionRedisRepository subscriptionRedisRepository;

    public void processReadSubscriptionMessage(String eventId) {
        InboxMessage subscriptionInbox = inboxRepository.findByFileId(eventId);
        log.info("Processing read subscription message for eventId={}",eventId);
        SubscriptionCacheDto subscriptionCacheDto = objectMapper
                .readValue(subscriptionInbox.getPayload(), SubscriptionCacheDto.class);
        subscriptionRedisRepository.save(subscriptionCacheDto);

    }
}
