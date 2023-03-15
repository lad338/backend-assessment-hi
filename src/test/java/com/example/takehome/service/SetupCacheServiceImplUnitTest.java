package com.example.takehome.service;

import com.example.takehome.model.trevorblades.response.Country;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.impl.SetupCacheServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SetupCacheServiceImplUnitTest {

  private TrevorBladesGraphQLService trevorBladesGraphQLService;
  private CachePutService cachePutService;
  private SetupCacheService setupCacheService;

  @BeforeEach
  void setup() {
    trevorBladesGraphQLService = Mockito.mock(TrevorBladesGraphQLService.class);
    cachePutService = Mockito.mock(CachePutService.class);

    setupCacheService = new SetupCacheServiceImpl(trevorBladesGraphQLService, cachePutService);

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
  void givenCallSetupCache_thenCallSourceAndPutCache() {
    assert (setupCacheService.setupCache());
    Mockito.verify(trevorBladesGraphQLService).getContinents();
    Mockito.verify(cachePutService).putContinentCodeByCountryCode("HK", "AS");
    Mockito.verify(cachePutService).putContinentCodeByCountryCode("CA", "NA");
    Mockito.verify(cachePutService).putContinentCodeByCountryCode("US", "NA");
    Mockito
      .verify(cachePutService)
      .putContinentByContinentCode(
        SourceContinent
          .builder()
          .code("NA")
          .name("North America")
          .countries(
            List.of(Country.builder().code("CA").build(), Country.builder().code("US").build())
          )
          .build()
      );

    Mockito
      .verify(cachePutService)
      .putContinentByContinentCode(
        SourceContinent
          .builder()
          .code("AS")
          .name("Asia")
          .countries(List.of(Country.builder().code("HK").build()))
          .build()
      );
  }

  @Test
  void givenCallIsSetUp_thenReturnFalse() {
    assert !setupCacheService.isSetup();
  }
}
