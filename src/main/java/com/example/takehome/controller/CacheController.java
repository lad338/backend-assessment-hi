package com.example.takehome.controller;

import com.example.takehome.model.api.response.DebugResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

  private final RedisTemplate redisTemplate;

  @Autowired
  public CacheController(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @GetMapping("/flushCache")
  DebugResponse flushCache() {
    redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();

    return DebugResponse.builder().message("done").build();
  }
}
