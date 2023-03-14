package com.example.takehome.model.trevorblades.response;

import lombok.Data;

@Data
public class GraphQLResponse<T> {

  private T data;
}
