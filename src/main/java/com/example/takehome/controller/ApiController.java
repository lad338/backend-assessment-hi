package com.example.takehome.controller;

import com.example.takehome.model.api.response.ApiResponse;
import com.example.takehome.model.trevorblades.response.Continent;
import com.example.takehome.service.TrevorBladesGraphQLService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

  private final TrevorBladesGraphQLService trevorBladesGraphQLService;

  @Autowired
  public ApiController(TrevorBladesGraphQLService trevorBladesGraphQLService) {
    this.trevorBladesGraphQLService = trevorBladesGraphQLService;
  }

  @GetMapping("/continents")
  public List<Continent> getContinents(@RequestParam(required = false) List<String> countries) {
    System.out.println(countries);
    return trevorBladesGraphQLService.getContinents();
  }
}
