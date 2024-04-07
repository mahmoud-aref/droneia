package com.elmenus.droneia.infrastructure.security.config;

import com.elmenus.droneia.infrastructure.datasource.sql.user.UserRepository;
import com.elmenus.droneia.infrastructure.security.filter.JwtAuthWebFilter;
import com.elmenus.droneia.infrastructure.security.service.JwtService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    private final UserRepository repository;
    private final JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> repository.findByUsername(username)
                .map(authUser -> User
                        .withUsername(authUser.getUsername())
                        .password(authUser.getPassword())
                        .authorities(authUser.getRole().name())
                        .accountExpired(!authUser.isActive())
                        .credentialsExpired(!authUser.isActive())
                        .disabled(!authUser.isActive())
                        .accountLocked(!authUser.isActive())
                        .build()
                );
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(@NotNull ServerHttpSecurity httpSecurity,
                                                      @NotNull ReactiveAuthenticationManager authenticationManager) {
        httpSecurity
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(authenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.POST).permitAll()
                        .pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers("/droneia/api/v1/auth/**").permitAll()
                        .pathMatchers("/api-docs/**", "/api-docs").permitAll()
                        .pathMatchers("/droneia/api/v1/drone/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .addFilterAt(new JwtAuthWebFilter(jwtService), SecurityWebFiltersOrder.AUTHENTICATION);


        return httpSecurity.build();
    }
}