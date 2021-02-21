package org.joulesjs.security;

import java.util.stream.Collectors;

import org.joulesjs.JoulesJsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JoulesJsProperties.class)
public class ConfigureAllowedIps extends WebSecurityConfigurerAdapter {
    @Autowired
    private JoulesJsProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String allowedIps = properties.getAllowedIps().stream()
                .map(allowedIp -> "hasIpAddress('" + allowedIp + "')").collect(Collectors.joining(" or "));

        http.authorizeRequests().antMatchers("/**").access(allowedIps);
    }
}