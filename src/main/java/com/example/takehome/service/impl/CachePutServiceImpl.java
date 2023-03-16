package com.example.takehome.service.impl;

import static com.example.takehome.constant.Redis.CONTINENT;
import static com.example.takehome.constant.Redis.COUNTRY;

import com.example.takehome.model.cache.CacheContinent;
import com.example.takehome.model.trevorblades.response.Country;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.CachePutService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class CachePutServiceImpl implements CachePutService {

  @CachePut(value = COUNTRY, key = "#countryCode")
  public String putContinentCodeByCountryCode(String countryCode, String continentCode) {
    return continentCode;
  }

  @CachePut(value = CONTINENT, key = "#continent.code")
  public CacheContinent putContinentByContinentCode(SourceContinent continent) {
    return CacheContinent
      .builder()
      .countries(continent.getCountries().stream().map(Country::getCode).toList())
      .name(continent.getName())
      .build();
  }
}
