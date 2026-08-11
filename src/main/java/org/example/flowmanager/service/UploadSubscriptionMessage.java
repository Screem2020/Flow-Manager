package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UploadSubscriptionMessage {

    @CacheEvict(value = "subscriptions", key = "#login")
    public void processReadSubscriptionMessage(String login) {
        log.info("Processing read subscription message for eventId={}",login);
    }
}
