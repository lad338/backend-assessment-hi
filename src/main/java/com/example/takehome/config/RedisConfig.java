package com.example.takehome.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Value("${redis.host}")
  private String host;

  @Value("${redis.port}")
  private String port;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    final LettuceConnectionFactory factory = new LettuceConnectionFactory();
    factory.getStandaloneConfiguration().setPort(Integer.parseInt(port));
    factory.getStandaloneConfiguration().setHostName(host);

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
