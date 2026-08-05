package org.example.flowmanager.controller;

import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "SUBSCRIPTION",
        path = "api/v1/subscription"
)
public interface SubscriptionClient {
    @GetMapping("/{login}")
    SubscriptionCacheDto getLoginSubscriptionService(@PathVariable("login")  String login);

}
