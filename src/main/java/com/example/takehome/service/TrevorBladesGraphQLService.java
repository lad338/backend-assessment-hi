package com.example.takehome.service;

import com.example.takehome.model.trevorblades.response.SourceContinent;
import java.util.List;

public interface TrevorBladesGraphQLService {
  List<SourceContinent> getContinents();
}
