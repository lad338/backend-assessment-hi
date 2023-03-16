package com.example.takehome.controller.advice;

import com.example.takehome.model.api.response.ApiError;
import com.example.takehome.model.error.TooManyCallsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice {

  @ExceptionHandler(value = TooManyCallsException.class)
  ResponseEntity<ApiError<String>> handleTooManyCallsException() {
    return ResponseEntity
      .status(429)
      .body(ApiError.<String>builder().error("Too Many Requests").build());
  }
}
