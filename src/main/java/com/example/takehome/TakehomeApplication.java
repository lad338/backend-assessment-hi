package com.example.takehome;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TakehomeApplication {

  public static void main(String[] args) {
    SpringApplication.run(TakehomeApplication.class, args);
    log.info("This is an info message");
    log.trace("This is a trace message");
    log.debug("This is a debug message");
    log.warn("This is a warn message");
    log.error("This is an error message");
  }
}
