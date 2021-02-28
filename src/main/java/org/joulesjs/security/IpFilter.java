package org.joulesjs.security;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joulesjs.JoulesJsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;

@Component
@EnableConfigurationProperties(JoulesJsProperties.class)
public final class IpFilter extends OncePerRequestFilter {

    private final Collection<IPAddress> allowedIpMatchers;

    @Autowired
    public IpFilter(JoulesJsProperties properties) {
        allowedIpMatchers =
            properties.getAllowedIps()
            .stream()
            .map(IPAddressString::new)
            .map(IPAddressString::getAddress)
            .map(IPAddress::toPrefixBlock)
            .collect(Collectors.toList());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        IPAddress requesterIp = new IPAddressString(request.getRemoteAddr()).getAddress();
        boolean requesterIpIsAllowed =
            allowedIpMatchers.stream()
            .filter(allowedIpMatcher -> allowedIpMatcher.contains(requesterIp))
            .findAny()
            .isPresent();

        if(requesterIpIsAllowed) {
            filterChain.doFilter(request, response);
        }
    }
}
