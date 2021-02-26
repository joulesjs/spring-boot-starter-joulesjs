package org.joulesjs;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "joulesjs")
public class JoulesJsProperties {
  private List<String> allowedIps = List.of("127.0.0.1", "0:0:0:0:0:0:0:1");

  public List<String> getAllowedIps() {
    return allowedIps;
  }

  public void setAllowedIps(List<String> allowedIps) {
    this.allowedIps = allowedIps;
  }
}
