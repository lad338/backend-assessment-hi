package com.example.takehome.service;

import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.model.trevorblades.response.Country;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.impl.CachePutServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CachePutServiceImplUnitTest {

  private CachePutService cachePutService;

  @BeforeEach
  void setup() {
    cachePutService = new CachePutServiceImpl();
  }

  @Test
  void givenCountryCode_thenReturnContinentCode() {
    assert (cachePutService.putContinentCodeByCountryCode("HK", "AS")).equals("AS");
  }

  @Test
  void givenSourceContinent_thenCacheContinent() {
    final SourceContinent sourceContinent = SourceContinent
      .builder()
      .name("Asia")
      .code("AS")
      .countries(
        List.of(Country.builder().code("HK").build(), Country.builder().code("JP").build())
      )
      .build();

    final CacheContinent expected = CacheContinent
      .builder()
      .countries(List.of("HK", "JP"))
      .name("Asia")
      .build();

    assert (cachePutService.putContinentByContinentCode(sourceContinent)).equals(expected);
  }
}
