package com.example.takehome.model.trevorblades.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SourceContinent {

  private String code;
  private String name;
  private List<Country> countries;
}
