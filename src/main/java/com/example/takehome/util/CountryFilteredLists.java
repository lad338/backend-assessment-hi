package com.example.takehome.util;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryFilteredLists {

  private List<String> countries;
  private List<String> otherCountries;
}
