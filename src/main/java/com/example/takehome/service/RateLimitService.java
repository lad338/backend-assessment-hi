package com.example.takehome.service;

public interface RateLimitService {
  boolean canAccessByIP(String ip);

  boolean canAccessByAuth(String auth);
}
