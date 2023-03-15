package com.example.takehome.service.impl;

import static com.example.takehome.constant.Redis.CONTINENT_VALUE;
import static com.example.takehome.constant.Redis.COUNTRY_VALUE;

import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.model.trevorblades.response.Country;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.CachePutService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class CachePutServiceImpl implements CachePutService {

  @CachePut(value = COUNTRY_VALUE, key = "#countryCode")
  public String putContinentCodeByCountryCode(String countryCode, String continentCode) {
    return continentCode;
  }

  @CachePut(value = CONTINENT_VALUE, key = "#continent.code")
  public CacheContinent putContinentByContinentCode(SourceContinent continent) {
    return CacheContinent
      .builder()
      .countries(continent.getCountries().stream().map(Country::getCode).toList())
      .name(continent.getName())
      .build();
  }
}
