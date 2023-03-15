package com.example.takehome.model.trevorblades.response;

import java.util.List;
import lombok.Data;

@Data
public class SourceContinent {

  private String code;
  private String name;
  private List<Country> countries;
}
