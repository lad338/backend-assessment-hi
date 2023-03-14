package com.example.takehome.graphql;

public class Query {

  public static final String CONTINENTS =
    """
      query {
        continents {
          code
          name
          countries {
            code
          }
        }
      }
    """;
}
