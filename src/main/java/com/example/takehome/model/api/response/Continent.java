package com.example.takehome.model.api.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Continent {

  private List<String> countries;
  private String name;
  private List<String> otherCountries;
}
