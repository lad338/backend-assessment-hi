package com.example.takehome.service.impl;

import static com.example.takehome.constant.Redis.*;

import com.example.takehome.service.RateLimitService;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimitServiceImpl implements RateLimitService {

  private final RedisTemplate<String, Integer> rateLimitTemplate;

  @Autowired
  public RateLimitServiceImpl(RedisTemplate<String, Integer> rateLimitTemplate) {
    this.rateLimitTemplate = rateLimitTemplate;
  }

  public boolean canAccessByIP(String ip) {
    final String key = getIpKey(ip);
    final Integer integer = rateLimitTemplate.opsForValue().get(key);
    if (integer == null) {
      rateLimitTemplate.opsForValue().set(key, 1, Duration.ofSeconds(1));
      return true;
    }

    rateLimitTemplate.opsForValue().increment(key, 1);
    return integer < 5;
  }

  public boolean canAccessByAuth(String auth) {
    final String key = getAuthKey(auth);
    final Integer integer = rateLimitTemplate.opsForValue().get(key);
    if (integer == null) {
      rateLimitTemplate.opsForValue().set(key, 1, Duration.ofSeconds(1));
      return true;
    }

    rateLimitTemplate.opsForValue().increment(key, 1);
    return integer < 20;
  }

  private String getIpKey(String ip) {
    return RATE_LIMIT + CONCATENATOR + RATE_LIMIT_IP + CONCATENATOR + ip;
  }

  private String getAuthKey(String ip) {
    return RATE_LIMIT + CONCATENATOR + RATE_LIMIT_AUTH + CONCATENATOR + ip;
  }
}
