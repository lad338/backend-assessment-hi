package com.example.takehome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TrevorBladesGraphQLConfig {

  private final WebClient webClient = WebClient
    .builder()
    .baseUrl("https://countries.trevorblades.com/graphql")
    .build();

  private final HttpGraphQlClient client = HttpGraphQlClient.create(webClient);

  @Bean
  HttpGraphQlClient trevorBladesGraphQLClient() {
    return client;
  }
}
