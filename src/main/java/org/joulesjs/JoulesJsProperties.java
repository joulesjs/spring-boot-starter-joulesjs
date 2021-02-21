package org.joulesjs;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "joulesjs")
public class JoulesJsProperties {
  private List<String> allowedIps = Collections.emptyList();

  public List<String> getAllowedIps() {
    return allowedIps;
  }

  public void setAllowedIps(List<String> allowedIps) {
    this.allowedIps = allowedIps;
  }
}
