package com.example.takehome.service;

import com.example.takehome.model.cache.CacheContinent;

public interface SearchCacheService {
  String getContinentCodeByCountryCode(String countryCode);

  CacheContinent getContinentByContinentCode(String continentCode);
}
