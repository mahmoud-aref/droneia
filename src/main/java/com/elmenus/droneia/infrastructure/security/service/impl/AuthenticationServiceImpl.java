package com.elmenus.droneia.infrastructure.security.service.impl;

import com.elmenus.droneia.domain.user.model.UserEntity;
import com.elmenus.droneia.infrastructure.security.exceptions.UsernameExistsException;
import com.elmenus.droneia.infrastructure.security.model.JWTResponse;
import com.elmenus.droneia.infrastructure.security.model.Role;
import com.elmenus.droneia.infrastructure.security.model.UserLoginRequest;
import com.elmenus.droneia.infrastructure.security.model.UserRegistrationRequest;
import com.elmenus.droneia.infrastructure.security.service.AuthenticationService;
import com.elmenus.droneia.infrastructure.security.service.JwtService;
import com.elmenus.droneia.infrastructure.datasource.sql.user.UserRepository;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReactiveAuthenticationManager authenticationManager;

    @Override
    @PrePersist
    public Mono<JWTResponse> registerUser(UserRegistrationRequest request) {
        return userRepository.existsByUsername(request.getUsername())
                .flatMap(userNameExist -> {
                    if (userNameExist) {
                        return Mono.error(new UsernameExistsException());
                    }
                    var user = UserEntity.builder()
                            .fullName(request.getName())
                            .username(request.getUsername())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(Role.valueOf(request.getRole()))
                            .active(true)
                            .build();
                    return userRepository.save(user);
                })
                .flatMap(savedUser -> {
                    var auth = new UsernamePasswordAuthenticationToken(savedUser.getUsername(), request.getPassword());
                    return authenticationManager.authenticate(auth);
                })
                .map(jwtService::generateToken)
                .map(token -> JWTResponse.builder().token(token).build());
    }

    @Override
    public Mono<JWTResponse> authenticateUser(UserLoginRequest request) {
        return this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()))
                .map(jwtService::generateToken)
                .map(token -> JWTResponse.builder().token(token).build());

    }
}