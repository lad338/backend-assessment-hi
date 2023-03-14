package com.example.takehome.service;

import com.example.takehome.model.trevorblades.response.Continent;
import java.util.List;

public interface TrevorBladesGraphQLService {
  List<Continent> getContinents();
}
