package com.example.takehome.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class CountryUtil {

  /**
   * CountryFilteredLists.countries = codes in filter that exists in fullList (in fullList order), others are ignored
   * CountryFilteredLists.otherCountries = codes in fullList but not in filter (in fullList order)
   *
   * @param fullList Full list of country codes
   * @param filter  List of countries to be selected
   * @return CountryFilteredLists;
   *
   */
  public static CountryFilteredLists filterCountries(List<String> fullList, List<String> filter) {
    // converting to set for removing duplicate and speeding up searching
    final Set<String> filterSet = new HashSet<>(filter);

    Map<Boolean, List<String>> partitionedCodes = fullList
      .stream()
      .collect(Collectors.partitioningBy(filterSet::contains));

    return CountryFilteredLists
      .builder()
      .countries(partitionedCodes.get(true))
      .otherCountries(partitionedCodes.get(false))
      .build();
  }
}
