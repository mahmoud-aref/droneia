package com.elmenus.droneia.infrastructure.security.service;

import com.elmenus.droneia.infrastructure.security.model.JWTResponse;
import com.elmenus.droneia.infrastructure.security.model.UserLoginRequest;
import com.elmenus.droneia.infrastructure.security.model.UserRegistrationRequest;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<JWTResponse> registerUser(UserRegistrationRequest request);

    Mono<JWTResponse> authenticateUser(UserLoginRequest request);
}