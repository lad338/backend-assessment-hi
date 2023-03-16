package com.example.takehome.service;

import static com.example.takehome.constant.Redis.*;

import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.service.impl.SearchCacheServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class SearchCacheServiceImplUnitTest {

  private SetupCacheService setupCacheService;
  private SearchCacheService searchCacheService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setup() {
    setupCacheService = Mockito.mock(SetupCacheService.class);

    final RedisTemplate<String, String> redisCountryTemplate = (RedisTemplate<String, String>) Mockito.mock(
      RedisTemplate.class
    );

    final RedisTemplate<String, CacheContinent> redisContinentTemplate = (RedisTemplate<String, CacheContinent>) Mockito.mock(
      RedisTemplate.class
    );

    searchCacheService =
      new SearchCacheServiceImpl(setupCacheService, redisCountryTemplate, redisContinentTemplate);

    @SuppressWarnings("unchecked")
    final ValueOperations<String, String> mockCountryOps = (ValueOperations<String, String>) Mockito.mock(
      ValueOperations.class
    );
    @SuppressWarnings("unchecked")
    final ValueOperations<String, CacheContinent> mockContinentOps = (ValueOperations<String, CacheContinent>) Mockito.mock(
      ValueOperations.class
    );

    Mockito.when(redisCountryTemplate.opsForValue()).thenReturn(mockCountryOps);
    Mockito.when(redisContinentTemplate.opsForValue()).thenReturn(mockContinentOps);
    Mockito.when(mockCountryOps.get(COUNTRY + CONCATENATOR + "US")).thenReturn("NA");
    Mockito
      .when(mockContinentOps.get(CONTINENT + CONCATENATOR + "NA"))
      .thenReturn(CacheContinent.builder().name("North America").countries(List.of("US")).build());
  }

  @Test
  public void givenCountryCode_whenCacheIsSetup_thenReturnNull() {
    Mockito.when(setupCacheService.isSetup()).thenReturn(true);
    assert searchCacheService.getContinentCodeByCountryCode("US") == null;
    Mockito.verify(setupCacheService, Mockito.times(0)).setupCache();
  }

  @Test
  public void givenCountryCode_whenNotCacheIsSetup_thenReturnValue() {
    Mockito.when(setupCacheService.isSetup()).thenReturn(false);
    assert searchCacheService.getContinentCodeByCountryCode("US").equals("NA");
    Mockito.verify(setupCacheService).setupCache();
  }

  @Test
  public void giveContinentCode_whenCacheIsSetup_thenReturnNull() {
    Mockito.when(setupCacheService.isSetup()).thenReturn(true);
    assert searchCacheService.getContinentByContinentCode("NA") == null;
    Mockito.verify(setupCacheService, Mockito.times(0)).setupCache();
  }

  @Test
  public void giveContinentCode_whenNotCacheIsSetup_thenReturnValue() {
    Mockito.when(setupCacheService.isSetup()).thenReturn(false);
    assert searchCacheService
      .getContinentByContinentCode("NA")
      .equals(CacheContinent.builder().name("North America").countries(List.of("US")).build());
    Mockito.verify(setupCacheService).setupCache();
  }
}
