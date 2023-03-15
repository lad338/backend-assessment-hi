package com.example.takehome.service.impl;

import static com.example.takehome.constant.Redis.*;

import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.model.error.SetupCacheException;
import com.example.takehome.service.SearchCacheService;
import com.example.takehome.service.SetupCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchCacheServiceImpl implements SearchCacheService {

  private final SetupCacheService setupCacheService;
  private final RedisTemplate<String, String> redisCountryTemplate;
  private final RedisTemplate<String, CacheContinent> redisContinentTemplate;

  @Autowired
  public SearchCacheServiceImpl(
    SetupCacheService setupCacheService,
    RedisTemplate<String, String> redisCountryTemplate,
    RedisTemplate<String, CacheContinent> redisContinentTemplate
  ) {
    this.setupCacheService = setupCacheService;
    this.redisCountryTemplate = redisCountryTemplate;
    this.redisContinentTemplate = redisContinentTemplate;
  }

  @Cacheable(value = COUNTRY_VALUE, key = "#countryCode", unless = "#result == null")
  public String getContinentCodeByCountryCode(String countryCode) {
    if (setupCacheService.isSetup()) {
      return null;
    }
    try {
      setupCacheService.setupCache();
    } catch (Exception e) {
      throw new SetupCacheException(e.getMessage());
    }

    return redisCountryTemplate.opsForValue().get(COUNTRY_VALUE + CONCATENATOR + countryCode);
  }

  @Cacheable(value = CONTINENT_VALUE, key = "#continentCode", unless = "#result == null")
  public CacheContinent getContinentByContinentCode(String continentCode) {
    if (setupCacheService.isSetup()) {
      return null;
    }
    try {
      setupCacheService.setupCache();
    } catch (Exception e) {
      throw new SetupCacheException(e.getMessage());
    }
    return redisContinentTemplate.opsForValue().get(CONTINENT_VALUE + CONCATENATOR + continentCode);
  }
}
