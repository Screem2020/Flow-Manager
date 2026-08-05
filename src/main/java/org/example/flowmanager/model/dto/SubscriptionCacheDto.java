package org.example.flowmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscriptionCacheDto {
    private String login;
    private String subscriptionType;
    private Instant expiresAt;
}
