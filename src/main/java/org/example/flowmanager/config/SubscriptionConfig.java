package org.example.flowmanager.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.flowmanager.model.enums.SubscriptionStatus;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "subscription.limits")
public class SubscriptionConfig {
    private Map<SubscriptionStatus, DataSize> free;
}
