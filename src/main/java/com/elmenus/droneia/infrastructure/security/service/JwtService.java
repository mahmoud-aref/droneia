package com.elmenus.droneia.infrastructure.security.service;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

public interface JwtService {

    String getUsernameFromToken(String token);

    String generateToken(Authentication authentication);

    Authentication getAuthentication(String token);

    String getTokenFromHeader(String bearerToken);
}