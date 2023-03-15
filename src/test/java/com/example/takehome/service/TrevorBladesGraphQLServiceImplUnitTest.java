package com.example.takehome.service;

import com.example.takehome.graphql.Query;
import com.example.takehome.model.trevorblades.response.Country;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.impl.TrevorBladesGraphQLServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import reactor.core.publisher.Mono;

public class TrevorBladesGraphQLServiceImplUnitTest {

  private TrevorBladesGraphQLService trevorBladesGraphQLService;

  static class MockValues {

    static final List<Country> asCountries = List.of(
      Country.builder().code("HK").build(),
      Country.builder().code("JP").build(),
      Country.builder().code("KR").build()
    );

    static final List<Country> naCountries = List.of(
      Country.builder().code("CA").build(),
      Country.builder().code("US").build()
    );

    static final SourceContinent asContinent = SourceContinent
      .builder()
      .countries(asCountries)
      .code("AS")
      .name("Asia")
      .build();

    static final SourceContinent naContinent = SourceContinent
      .builder()
      .countries(naCountries)
      .code("NA")
      .name("North America")
      .build();
  }

  @BeforeEach
  public void mockGraphQLResponse() {
    final HttpGraphQlClient graphQLClient = Mockito.mock(HttpGraphQlClient.class);
    trevorBladesGraphQLService = new TrevorBladesGraphQLServiceImpl(graphQLClient);

    final GraphQlClient.RequestSpec mockRequestSpec = Mockito.mock(GraphQlClient.RequestSpec.class);
    final GraphQlClient.RetrieveSpec mockRetrieveSpec = Mockito.mock(
      GraphQlClient.RetrieveSpec.class
    );

    final Mono<List<SourceContinent>> mockResultMono = Mono.fromSupplier(() ->
      List.of(MockValues.asContinent, MockValues.naContinent)
    );

    Mockito.when(graphQLClient.document(Query.CONTINENTS)).thenReturn(mockRequestSpec);
    Mockito.when(mockRequestSpec.retrieve("continents")).thenReturn(mockRetrieveSpec);
    Mockito.when(mockRetrieveSpec.toEntityList(SourceContinent.class)).thenReturn(mockResultMono);
  }

  @Test
  public void givenSourceCalledFromGraphQL_thenReturnSuccessfully() {
    assert (trevorBladesGraphQLService.getContinents()).containsAll(
        List.of(MockValues.asContinent, MockValues.naContinent)
      );
  }
}
