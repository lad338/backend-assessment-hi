package com.example.takehome.model.cache;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Continent {

  private String name;
  private List<String> countries;
}
