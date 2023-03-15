package com.example.takehome.model.trevorblades.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceContinent {

  private String code;
  private String name;
  private List<Country> countries;
}
