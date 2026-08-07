package org.example.flowmanager.repository;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class SubscriptionRedisRepository {
    private final RedisTemplate<String, SubscriptionCacheDto> redisTemplate;

    public Optional<SubscriptionCacheDto> findByLogin(String login) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(login));
    }

    public void save(SubscriptionCacheDto dto) {
        redisTemplate.opsForValue().set(dto.getLogin(), dto);
    }

    public void delete(String login) {
        redisTemplate.delete(login);
    }

}
