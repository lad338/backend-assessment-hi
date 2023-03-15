package com.example.takehome.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

  private final RedisSerializer<Object> objectRedisSerializer;

  @Autowired
  public RedisCacheConfig(RedisSerializer<Object> objectRedisSerializer) {
    this.objectRedisSerializer = objectRedisSerializer;
  }

  @Bean
  public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration
      .defaultCacheConfig()
      .entryTtl(Duration.ofMinutes(60))
      .disableCachingNullValues()
      .serializeValuesWith(
        RedisSerializationContext.SerializationPair.fromSerializer(objectRedisSerializer)
      );
  }
}
