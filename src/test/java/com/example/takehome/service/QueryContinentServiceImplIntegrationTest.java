package com.example.takehome.service;

import static com.example.takehome.constant.Redis.CONCATENATOR;
import static com.example.takehome.constant.Redis.COUNTRY_VALUE;

import com.example.takehome.TestTakehomeApplication;
import com.example.takehome.config.RedisConfig;
import com.example.takehome.model.api.response.Continent;
import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.model.trevorblades.response.Country;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.impl.CachePutServiceImpl;
import com.example.takehome.service.impl.QueryContinentServiceImpl;
import com.example.takehome.service.impl.SearchCacheServiceImpl;
import com.example.takehome.service.impl.SetupCacheServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({ RedisConfig.class })
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = { CacheAutoConfiguration.class, RedisAutoConfiguration.class })
@EnableCaching
@SpringBootTest(classes = TestTakehomeApplication.class)
public class QueryContinentServiceImplIntegrationTest {

  @MockBean
  TrevorBladesGraphQLService trevorBladesGraphQLService;

  @TestConfiguration
  static class QueryContinentServiceImplIntegrationTestConfiguration {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    TrevorBladesGraphQLService trevorBladesGraphQLService;

    @Bean
    RedisTemplate<String, String> redisCountryTemplate() {
      final RedisTemplate<String, String> template = new RedisTemplate<>();
      template.setConnectionFactory(redisConnectionFactory);
      template.setKeySerializer(new StringRedisSerializer());
      template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
      return template;
    }

    @Bean
    RedisTemplate<String, CacheContinent> redisContinentTemplate() {
      final RedisTemplate<String, CacheContinent> template = new RedisTemplate<>();
      template.setConnectionFactory(redisConnectionFactory);
      template.setKeySerializer(new StringRedisSerializer());
      template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
      return template;
    }

    @Bean
    CachePutService cachePutService() {
      return new CachePutServiceImpl();
    }

    @Bean
    SetupCacheService setupCacheService() {
      return new SetupCacheServiceImpl(trevorBladesGraphQLService, cachePutService());
    }

    @Bean
    SearchCacheService searchCacheService() {
      return new SearchCacheServiceImpl(
        setupCacheService(),
        redisCountryTemplate(),
        redisContinentTemplate()
      );
    }

    @Bean
    QueryContinentService queryContinentService() {
      return new QueryContinentServiceImpl(searchCacheService());
    }
  }

  @Autowired
  private QueryContinentService queryContinentService;

  @Autowired
  private SetupCacheService setupCacheService;

  @Autowired
  private RedisTemplate<String, String> redisCountryTemplate;

  @Autowired
  private RedisTemplate<String, CacheContinent> redisContinentTemplate;

  @BeforeEach
  void setup() {
    Mockito
      .when(trevorBladesGraphQLService.getContinents())
      .thenReturn(
        List.of(
          SourceContinent
            .builder()
            .code("NA")
            .name("North America")
            .countries(
              List.of(Country.builder().code("CA").build(), Country.builder().code("US").build())
            )
            .build(),
          SourceContinent
            .builder()
            .code("AS")
            .name("Asia")
            .countries(List.of(Country.builder().code("HK").build()))
            .build()
        )
      );
  }

  @Test
  void givenCacheMiss_whenCacheNotSetup_thenCallSourceAndReturnFromRedisTemplate() {
    final List<Continent> expected = List.of(
      Continent
        .builder()
        .name("Asia")
        .otherCountries(Collections.emptyList())
        .countries(List.of("HK"))
        .build(),
      Continent
        .builder()
        .name("North America")
        .otherCountries(List.of("CA"))
        .countries(List.of("US"))
        .build()
    );

    assert !setupCacheService.isSetup();
    assert redisCountryTemplate.opsForValue().get(COUNTRY_VALUE + CONCATENATOR + "US") == null;
    assert redisCountryTemplate.opsForValue().get(COUNTRY_VALUE + CONCATENATOR + "HK") == null;

    final List<Continent> cacheMiss = queryContinentService.queryContinents(List.of("US", "HK"));

    assert cacheMiss.containsAll(expected);

    final List<Continent> cacheHit = queryContinentService.queryContinents(List.of("US", "HK"));

    assert cacheHit.containsAll(expected);

    Mockito.verify(trevorBladesGraphQLService, Mockito.times(1)).getContinents();

    assert "NA".equals(redisCountryTemplate.opsForValue().get(COUNTRY_VALUE + CONCATENATOR + "US"));
    assert "AS".equals(redisCountryTemplate.opsForValue().get(COUNTRY_VALUE + CONCATENATOR + "HK"));

    assert setupCacheService.isSetup();
  }
}
