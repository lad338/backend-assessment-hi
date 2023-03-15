package com.example.takehome.model.cache;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheContinent {

  @JsonProperty("name")
  private String name;

  @JsonProperty("countries")
  private List<String> countries;
}
