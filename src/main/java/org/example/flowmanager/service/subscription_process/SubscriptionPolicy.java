package org.example.flowmanager.service.subscription_process;

import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface SubscriptionPolicy {
    void determiningTariff(SubscriptionCacheDto subscriptionCacheDto, MultipartFile file);
}
