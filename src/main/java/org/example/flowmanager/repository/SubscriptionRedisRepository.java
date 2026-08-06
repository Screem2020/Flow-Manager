package org.example.flowmanager.repository;

import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRedisRepository extends JpaRepository<SubscriptionCacheDto, String> {
    boolean existsByLogin(String login);
    SubscriptionCacheDto findByLogin(String login);
}
