package com.example.takehome.interceptor;

import com.example.takehome.model.error.TooManyCallsException;
import com.example.takehome.service.RateLimitService;
import com.example.takehome.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

  final RateLimitService rateLimitService;

  @Autowired
  public RateLimitInterceptor(RateLimitService rateLimitService) {
    this.rateLimitService = rateLimitService;
  }

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    final String auth = request.getHeader("Authorization");

    if (auth != null && !auth.equals("")) {
      if (rateLimitService.canAccessByAuth(auth)) {
        return true;
      }
      throw new TooManyCallsException();
    }

    final String ip = HttpUtil.getRequestIP(request);

    if (rateLimitService.canAccessByIP(ip)) {
      return true;
    }
    throw new TooManyCallsException();
  }
}
