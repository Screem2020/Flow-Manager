package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.controller.SubscriptionClient;
import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionClient subscriptionClient;

    @Cacheable(value = "subscriptions", key = "#login")
    public SubscriptionCacheDto checkSubscriptionLogin(String login) {
        return subscriptionClient.getLoginSubscriptionService(login);
    }
}
