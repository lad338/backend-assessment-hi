package com.example.takehome.service.impl;

import com.example.takehome.graphql.Query;
import com.example.takehome.model.trevorblades.response.SourceContinent;
import com.example.takehome.service.TrevorBladesGraphQLService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;

@Service
public class TrevorBladesGraphQLServiceImpl implements TrevorBladesGraphQLService {

  private final HttpGraphQlClient graphQLClient;

  @Autowired
  public TrevorBladesGraphQLServiceImpl(HttpGraphQlClient graphQLClient) {
    this.graphQLClient = graphQLClient;
  }

  @Override
  public List<SourceContinent> getContinents() {
    return graphQLClient
      .document(Query.CONTINENTS)
      .retrieve("continents")
      .toEntityList(SourceContinent.class)
      .block();
  }
}
