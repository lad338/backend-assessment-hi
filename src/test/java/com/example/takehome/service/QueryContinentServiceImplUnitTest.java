package com.example.takehome.service;

import com.example.takehome.model.api.response.Continent;
import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.service.impl.QueryContinentServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class QueryContinentServiceImplUnitTest {

  private QueryContinentService queryContinentService;

  @BeforeEach
  public void mockSearchCacheService() {
    final SearchCacheService searchCacheService = Mockito.mock(SearchCacheService.class);
    queryContinentService = new QueryContinentServiceImpl(searchCacheService);

    final CacheContinent naContinent = CacheContinent
      .builder()
      .countries(List.of("CA", "US"))
      .name("North America")
      .build();
    final CacheContinent asContinent = CacheContinent
      .builder()
      .countries(List.of("HK", "JP", "KR"))
      .name("Asia")
      .build();

    Mockito.when(searchCacheService.getContinentCodeByCountryCode("US")).thenReturn("NA");
    Mockito.when(searchCacheService.getContinentCodeByCountryCode("CA")).thenReturn("NA");
    Mockito.when(searchCacheService.getContinentCodeByCountryCode("HK")).thenReturn("AS");
    Mockito.when(searchCacheService.getContinentCodeByCountryCode("JP")).thenReturn("AS");
    Mockito.when(searchCacheService.getContinentCodeByCountryCode("KR")).thenReturn("AS");

    Mockito.when(searchCacheService.getContinentByContinentCode("NA")).thenReturn(naContinent);
    Mockito.when(searchCacheService.getContinentByContinentCode("AS")).thenReturn(asContinent);
  }

  @Test
  public void givenHK_thenReturnAS() {
    final List<Continent> expectedContinents = List.of(
      Continent
        .builder()
        .countries(List.of("HK"))
        .otherCountries(List.of("JP", "KR"))
        .name("Asia")
        .build()
    );

    assert (queryContinentService.queryContinents(List.of("HK"))).equals(expectedContinents);
  }

  @Test
  public void givenHKUS_thenReturnASNA() {
    final List<Continent> expectedContinents = List.of(
      Continent
        .builder()
        .countries(List.of("HK"))
        .otherCountries(List.of("JP", "KR"))
        .name("Asia")
        .build(),
      Continent
        .builder()
        .countries(List.of("US"))
        .otherCountries(List.of("CA"))
        .name("North America")
        .build()
    );

    assert (queryContinentService.queryContinents(List.of("HK", "US"))).containsAll(
        expectedContinents
      );
  }

  @Test
  public void givenEmpty_thenReturnEmpty() {
    assert (queryContinentService.queryContinents(Collections.emptyList())).isEmpty();
  }

  @Test
  public void givenList_whenDifferentOrder_thenReturnTheSameCountryOrderInContinent() {
    final List<Continent> expected = List.of(
      Continent
        .builder()
        .countries(List.of("JP", "KR"))
        .otherCountries(List.of("HK"))
        .name("Asia")
        .build()
    );

    assert (queryContinentService.queryContinents(List.of("JP", "KR"))).equals(expected);
    assert (queryContinentService.queryContinents(List.of("KR", "JP"))).equals(expected);
  }

  @Test
  public void givenJunk_thenReturnSuccessfully() {
    final List<Continent> expected = List.of(
      Continent
        .builder()
        .countries(List.of("JP", "KR"))
        .otherCountries(List.of("HK"))
        .name("Asia")
        .build()
    );

    assert (queryContinentService.queryContinents(List.of("JP", "KR", "JUNK"))).equals(expected);
  }
}
