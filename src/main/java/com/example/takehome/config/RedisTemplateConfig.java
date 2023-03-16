package com.example.takehome.config;

import com.example.takehome.model.cache.CacheContinent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateConfig {

  private final RedisConnectionFactory redisConnectionFactory;

  private final RedisSerializer<Object> objectRedisSerializer;
  private final RedisSerializer<String> stringRedisSerializer;

  @Autowired
  public RedisTemplateConfig(
    RedisConnectionFactory redisConnectionFactory,
    RedisSerializer<Object> objectRedisSerializer,
    RedisSerializer<String> stringRedisSerializer
  ) {
    this.redisConnectionFactory = redisConnectionFactory;
    this.objectRedisSerializer = objectRedisSerializer;
    this.stringRedisSerializer = stringRedisSerializer;
  }

  @Bean(name = "redisCountryTemplate")
  public RedisTemplate<String, String> redisCountryTemplate() {
    final RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(stringRedisSerializer);
    template.setDefaultSerializer(objectRedisSerializer);
    return template;
  }

  @Bean(name = "redisContinentTemplate")
  public RedisTemplate<String, CacheContinent> redisContinentTemplate() {
    final RedisTemplate<String, CacheContinent> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(stringRedisSerializer);
    template.setDefaultSerializer(objectRedisSerializer);
    return template;
  }

  @Bean(name = "rateLimitTemplate")
  public RedisTemplate<String, Integer> rateLimitTemplate() {
    final RedisTemplate<String, Integer> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(stringRedisSerializer);
    template.setDefaultSerializer(objectRedisSerializer);
    return template;
  }
}
