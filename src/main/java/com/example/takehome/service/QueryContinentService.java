package com.example.takehome.service;

import com.example.takehome.model.api.response.Continent;
import java.util.List;

public interface QueryContinentService {
  List<Continent> queryContinents(List<String> countryCodes);
}
