package com.example.takehome.controller;

import com.example.takehome.model.api.response.ApiResponse;
import com.example.takehome.service.QueryContinentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

  private final QueryContinentService queryContinentService;

  @Autowired
  public ApiController(QueryContinentService queryContinentService) {
    this.queryContinentService = queryContinentService;
  }

  @GetMapping("/continents")
  public ApiResponse getContinents(@RequestParam(required = false) List<String> countries) {
    return ApiResponse
      .builder()
      .continents(queryContinentService.queryContinents(countries))
      .build();
  }
}
