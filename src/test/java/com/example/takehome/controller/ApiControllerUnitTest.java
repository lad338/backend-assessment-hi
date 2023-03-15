package com.example.takehome.controller;

import com.example.takehome.model.api.response.ApiResponse;
import com.example.takehome.model.api.response.Continent;
import com.example.takehome.service.QueryContinentService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ApiControllerUnitTest {

  private QueryContinentService queryContinentService;

  private ApiController apiController;

  @BeforeEach
  void setup() {
    queryContinentService = Mockito.mock(QueryContinentService.class);

    apiController = new ApiController(queryContinentService);

    final List<Continent> mockContinents = List.of(
      Continent
        .builder()
        .name("Asia")
        .countries(List.of("HK"))
        .otherCountries(List.of("JP"))
        .build(),
      Continent
        .builder()
        .name("North America")
        .countries(List.of("CA"))
        .otherCountries(List.of("US"))
        .build()
    );

    Mockito
      .when(queryContinentService.queryContinents(List.of("CA", "HK")))
      .thenReturn(mockContinents);
  }

  @Test
  void givenCAHK_thenReturnSuccessfully() {
    final ApiResponse apiResponse = apiController.getContinents(List.of("CA", "HK"));

    assert (apiResponse).getContinents()
      .containsAll(
        List.of(
          Continent
            .builder()
            .name("Asia")
            .countries(List.of("HK"))
            .otherCountries(List.of("JP"))
            .build(),
          Continent
            .builder()
            .name("North America")
            .countries(List.of("CA"))
            .otherCountries(List.of("US"))
            .build()
        )
      );

    Mockito.verify(queryContinentService).queryContinents(List.of("CA", "HK"));
  }
}
