package com.example.takehome.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class TestCountryUtil {

  private static final List<String> FULL_LIST = Arrays.asList("A", "B", "C", "D", "E");
  private static final List<String> FILTER_ABC = Arrays.asList("A", "B", "C");
  private static final List<String> FILTER_CBA = Arrays.asList("C", "B", "A");
  private static final List<String> FILTER_XY = Arrays.asList("X", "Y");
  private static final List<String> FILTER_AX = Arrays.asList("A", "X");

  @Test
  void givenFullListAndFilterABC_thenReturnSuccessfully() {
    final CountryFilteredLists expected = CountryFilteredLists
      .builder()
      .countries(FILTER_ABC)
      .otherCountries(Arrays.asList("D", "E"))
      .build();

    assert (CountryUtil.filterCountries(FULL_LIST, FILTER_ABC).equals(expected));
  }

  @Test
  void givenFullListAndFilterCBA_thenReturnSuccessfully() {
    final CountryFilteredLists expected = CountryFilteredLists
      .builder()
      .countries(FILTER_ABC)
      .otherCountries(Arrays.asList("D", "E"))
      .build();

    assert (CountryUtil.filterCountries(FULL_LIST, FILTER_CBA).equals(expected));
  }

  @Test
  void givenFullListAndFilterXY_thenReturnSuccessfully() {
    final CountryFilteredLists expected = CountryFilteredLists
      .builder()
      .countries(Collections.emptyList())
      .otherCountries(FULL_LIST)
      .build();

    assert (CountryUtil.filterCountries(FULL_LIST, FILTER_XY).equals(expected));
  }

  @Test
  void givenFullListAndFilterAX_thenReturnSuccessfully() {
    final CountryFilteredLists expected = CountryFilteredLists
      .builder()
      .countries(Collections.singletonList("A"))
      .otherCountries(Arrays.asList("B", "C", "D", "E"))
      .build();

    assert (CountryUtil.filterCountries(FULL_LIST, FILTER_AX).equals(expected));
  }

  @Test
  void givenFullListAndFilter_whenMock_thenReturnMockSuccessfully() {
    try (MockedStatic<CountryUtil> util = Mockito.mockStatic(CountryUtil.class)) {
      util
        .when(() ->
          CountryUtil.filterCountries(Arrays.asList("A", "B", "C"), Collections.singletonList("B"))
        )
        .thenReturn(
          CountryFilteredLists
            .builder()
            .countries(Collections.singletonList("B"))
            .otherCountries(Arrays.asList("A", "C"))
            .build()
        );
    }
  }
}
