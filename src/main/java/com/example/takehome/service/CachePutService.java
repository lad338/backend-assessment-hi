package com.example.takehome.service;

import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.model.trevorblades.response.SourceContinent;

public interface CachePutService {
  String putContinentCodeByCountryCode(String countryCode, String continentCode);

  CacheContinent putContinentByContinentCode(SourceContinent continent);
}
