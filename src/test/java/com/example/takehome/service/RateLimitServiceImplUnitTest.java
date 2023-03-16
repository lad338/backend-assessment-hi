package com.example.takehome.service;

import static com.example.takehome.constant.Redis.*;

import com.example.takehome.service.impl.RateLimitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RateLimitServiceImplUnitTest {

  private RateLimitService rateLimitService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setup() {
    final RedisTemplate<String, Integer> rateLimitTemplate = (RedisTemplate<String, Integer>) Mockito.mock(
      RedisTemplate.class
    );

    final ValueOperations<String, Integer> mockOps = Mockito.mock(ValueOperations.class);

    rateLimitService = new RateLimitServiceImpl(rateLimitTemplate);

    Mockito.when(rateLimitTemplate.opsForValue()).thenReturn(mockOps);

    Mockito
      .when(mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_IP + CONCATENATOR + "127.0.0.1"))
      .thenReturn(null);
    Mockito
      .when(mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_IP + CONCATENATOR + "192.168.1.1"))
      .thenReturn(1);
    Mockito
      .when(mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_IP + CONCATENATOR + "192.168.1.4"))
      .thenReturn(4);
    Mockito
      .when(mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_IP + CONCATENATOR + "192.168.1.5"))
      .thenReturn(5);

    Mockito
      .when(mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_AUTH + CONCATENATOR + "clean"))
      .thenReturn(null);
    Mockito
      .when(mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_AUTH + CONCATENATOR + "once"))
      .thenReturn(1);
    Mockito
      .when(mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_AUTH + CONCATENATOR + "edge"))
      .thenReturn(19);
    Mockito
      .when(
        mockOps.get(RATE_LIMIT + CONCATENATOR + RATE_LIMIT_AUTH + CONCATENATOR + "played too much")
      )
      .thenReturn(20);
  }

  @Test
  public void givenCleanIP_thenReturnTrue() {
    assert rateLimitService.canAccessByIP("127.0.0.1");
  }

  @Test
  public void givenOnceIP_thenReturnTrue() {
    assert rateLimitService.canAccessByIP("192.168.1.1");
  }

  @Test
  public void given4TimesIP_thenReturnTrue() {
    assert rateLimitService.canAccessByIP("192.168.1.4");
  }

  @Test
  public void given5TimesIP_thenReturnFalse() {
    assert !rateLimitService.canAccessByIP("192.168.1.5");
  }

  @Test
  public void givenCleanAuth_thenReturnTrue() {
    assert rateLimitService.canAccessByAuth("clean");
  }

  @Test
  public void givenOnceAuth_thenReturnTrue() {
    assert rateLimitService.canAccessByAuth("once");
  }

  @Test
  public void given19TimesAuth_thenReturnTrue() {
    assert rateLimitService.canAccessByAuth("edge");
  }

  @Test
  public void given20TimesAuth_thenReturnFalse() {
    assert !rateLimitService.canAccessByAuth("played too much");
  }
}
