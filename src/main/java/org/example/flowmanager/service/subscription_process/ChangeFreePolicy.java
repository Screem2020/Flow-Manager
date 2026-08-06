package org.example.flowmanager.service.subscription_process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.config.SubscriptionConfig;
import org.example.flowmanager.exception.FileErrorLimitException;
import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.example.flowmanager.model.enums.SubscriptionStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeFreePolicy implements SubscriptionPolicy {

    private final SubscriptionConfig subscriptionConfig;

    @Override
    public void determiningTariff(SubscriptionCacheDto subscriptionCacheDto, MultipartFile file) {
        Map<SubscriptionStatus, DataSize> limitCard = subscriptionConfig.getFree();
        DataSize dataSize = limitCard.get(subscriptionCacheDto.getSubscriptionType());
        log.info("Tariff free file size: {} and limits: {}", file.getSize(), dataSize.toBytes());
        if (dataSize.toBytes() < file.getSize()) {
            log.error("Tariff free data size more limits: {}", dataSize.toBytes());
            throw new FileErrorLimitException("File size is larger than requested size");
        }
    }
}
