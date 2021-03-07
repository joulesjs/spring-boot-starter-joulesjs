package org.joulesjs;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "joulesjs")
public class JoulesJsProperties {
  private List<String> allowedIps = List.of("127.0.0.1", "0:0:0:0:0:0:0:1");
  private List<String> command = List.of();
  private Boolean allowHeadless = true;

  public List<String> getAllowedIps() {
    return allowedIps;
  }

  public void setAllowedIps(List<String> allowedIps) {
    this.allowedIps = allowedIps;
  }

  public List<String> getCommand() {
    return this.command;
  }

  public void setCommand(List<String> command) {
    this.command = command;
  }

  public Boolean getAllowHeadless() {
    return this.allowHeadless;
  }

  public void setAllowHeadless(Boolean allowHeadless) {
    this.allowHeadless = allowHeadless;
  }
}
