package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.controller.SubscriptionClient;
import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.example.flowmanager.repository.SubscriptionRedisRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionClient subscriptionClient;
    private final SubscriptionRedisRepository subscriptionRedisRepository;

    //    public SubscriptionCacheDto checkSubscriptionLogin(String login) {
//        if (subscriptionRedisRepository.existsByLogin(login)) {
//            log.info("Subscription exists for login {}", login);
//            return subscriptionRedisRepository.findByLogin(login);
//        } else {
//            log.info("Subscription not found for login {} save to Redis", login);
//            SubscriptionCacheDto loginSubscriptionService = subscriptionClient.getLoginSubscriptionService(login);
//            subscriptionRedisRepository.save(loginSubscriptionService);
//            return  loginSubscriptionService;
//        }
//    }
    public SubscriptionCacheDto checkSubscriptionLogin(String login) {
        Optional<SubscriptionCacheDto> dto = subscriptionRedisRepository.findByLogin(login);
        if (dto.isPresent()) {
            log.info("Subscription login exists {}", login);
            return dto.get();
        } else {
            log.info("Subscription not found for login {} save to Redis", login);
            SubscriptionCacheDto loginSubscriptionService = subscriptionClient.getLoginSubscriptionService(login);
            subscriptionRedisRepository.save(loginSubscriptionService);
            return loginSubscriptionService;
        }
    }
}
