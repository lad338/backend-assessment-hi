package com.example.takehome.service.impl;

import static com.example.takehome.constant.Redis.STATUS_VALUE;

import com.example.takehome.model.trevorblades.response.Country;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.CachePutService;
import com.example.takehome.service.SetupCacheService;
import com.example.takehome.service.TrevorBladesGraphQLService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SetupCacheServiceImpl implements SetupCacheService {

  private final TrevorBladesGraphQLService trevorBladesGraphQLService;
  private final CachePutService cachePutService;

  @Autowired
  public SetupCacheServiceImpl(
    TrevorBladesGraphQLService trevorBladesGraphQLService,
    CachePutService cachePutService
  ) {
    this.trevorBladesGraphQLService = trevorBladesGraphQLService;
    this.cachePutService = cachePutService;
  }

  @CachePut(value = STATUS_VALUE)
  public boolean setupCache() {
    List<SourceContinent> sourceContinents = trevorBladesGraphQLService.getContinents();

    sourceContinents.forEach(continent -> {
      cachePutService.putContinentByContinentCode(continent);
      for (Country country : continent.getCountries()) {
        cachePutService.putContinentCodeByCountryCode(country.getCode(), continent.getCode());
      }
    });

    return true;
  }

  @Cacheable(value = STATUS_VALUE)
  public boolean isSetup() {
    return false;
  }
}
