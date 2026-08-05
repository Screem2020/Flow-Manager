package org.example.flowmanager.config;

import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {
    @Bean
    RedisTemplate<String, SubscriptionCacheDto> serializerInfoUserDtoRedis(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, SubscriptionCacheDto> сacheDtoRedisTemplate = new RedisTemplate<>();
        сacheDtoRedisTemplate.setConnectionFactory(redisConnectionFactory);
        сacheDtoRedisTemplate.setKeySerializer(new StringRedisSerializer());
        сacheDtoRedisTemplate.setValueSerializer(new GenericJacksonJsonRedisSerializer(objectMapper));
        return сacheDtoRedisTemplate;
    }
}
