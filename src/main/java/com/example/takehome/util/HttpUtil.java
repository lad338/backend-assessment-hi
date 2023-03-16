package com.example.takehome.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public final class HttpUtil {

  private static final List<String> IP_HEADERS = List.of(
    "X-Forwarded-For",
    "Proxy-Client-IP",
    "WL-Proxy-Client-IP",
    "HTTP_X_FORWARDED_FOR",
    "HTTP_X_FORWARDED",
    "HTTP_X_CLUSTER_CLIENT_IP",
    "HTTP_CLIENT_IP",
    "HTTP_FORWARDED_FOR",
    "HTTP_FORWARDED",
    "HTTP_VIA",
    "REMOTE_ADDR"
  );

  public static String getRequestIP(HttpServletRequest request) {
    for (String header : IP_HEADERS) {
      String value = request.getHeader(header);
      if (value == null || value.isEmpty()) {
        continue;
      }
      String[] parts = value.split("\\s*,\\s*");
      return parts[0];
    }
    return request.getRemoteAddr();
  }
}
