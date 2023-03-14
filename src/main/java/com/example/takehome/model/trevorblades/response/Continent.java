package com.example.takehome.model.trevorblades.response;

import java.util.List;
import lombok.Data;

@Data
public class Continent {

  private String code;
  private String name;
  private List<Country> countries;
}
