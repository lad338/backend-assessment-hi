package com.example.takehome.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;

@TestConfiguration
public class RedisConfig {

  public static final int REDIS_PORT = 6370;

  private final RedisServer redisServer;

  public RedisConfig() throws IOException {
    redisServer = new RedisServer(REDIS_PORT);
  }

  @PostConstruct
  public void postConstruct() throws IOException {
    redisServer.start();
  }

  @PreDestroy
  public void preDestroy() throws IOException {
    redisServer.stop();
  }

  @Bean
  RedisConnectionFactory redisConnectionFactory() {
    final LettuceConnectionFactory factory = new LettuceConnectionFactory();
    factory.getStandaloneConfiguration().setPort(REDIS_PORT);
    return factory;
  }

  @Bean
  public RedisSerializer<Object> objectRedisSerializer() {
    return new GenericJackson2JsonRedisSerializer();
  }

  @Bean
  public RedisSerializer<String> stringRedisSerializer() {
    return new StringRedisSerializer();
  }
}
