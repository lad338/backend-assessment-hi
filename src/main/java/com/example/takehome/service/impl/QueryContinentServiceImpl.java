package com.example.takehome.service.impl;

import static com.example.takehome.constant.Redis.CONCATENATOR;
import static com.example.takehome.constant.Redis.COUNTRY_VALUE;

import com.example.takehome.model.api.response.Continent;
import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.service.QueryContinentService;
import com.example.takehome.service.SearchCacheService;
import com.example.takehome.util.CountryFilteredLists;
import com.example.takehome.util.CountryUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryContinentServiceImpl implements QueryContinentService {

  private final SearchCacheService searchCacheService;

  @Autowired
  public QueryContinentServiceImpl(SearchCacheService searchCacheService) {
    this.searchCacheService = searchCacheService;
  }

  public List<Continent> queryContinents(List<String> queryCountryCodes) {
    if (queryCountryCodes.isEmpty()) {
      return Collections.emptyList();
    }

    final Map<String, List<String>> countryCodesByContinentCodes = queryCountryCodes
      .stream()
      .collect(
        Collectors.groupingBy(countryCode ->
          Optional
            .ofNullable(searchCacheService.getContinentCodeByCountryCode(countryCode))
            .orElse("")
        )
      );

    countryCodesByContinentCodes.remove("");

    return countryCodesByContinentCodes
      .entrySet()
      .stream()
      .map(entry -> {
        final CacheContinent continentByContinentCode = searchCacheService.getContinentByContinentCode(
          entry.getKey()
        );
        final CountryFilteredLists countryFilteredLists = CountryUtil.filterCountries(
          continentByContinentCode.getCountries(),
          entry.getValue()
        );
        return Continent
          .builder()
          .countries(countryFilteredLists.getCountries())
          .otherCountries(countryFilteredLists.getOtherCountries())
          .name(continentByContinentCode.getName())
          .build();
      })
      .toList();
  }
}
