package org.example.flowmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowmanager.model.enums.SubscriptionStatus;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscriptionCacheDto {
    private String login;
    private SubscriptionStatus subscriptionType;
    private Instant expiresAt;
}
