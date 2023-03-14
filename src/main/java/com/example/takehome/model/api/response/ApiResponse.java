package com.example.takehome.model.api.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

  private List<Continent> continents;
}
