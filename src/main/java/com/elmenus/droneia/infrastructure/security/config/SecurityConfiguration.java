package com.elmenus.droneia.infrastructure.security.config;

import com.elmenus.droneia.infrastructure.security.filter.JwtAuthWebFilter;
import com.elmenus.droneia.infrastructure.security.repository.SecurityContextRepository;
import com.elmenus.droneia.infrastructure.security.service.JwtService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    private final JwtService jwtService;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityFilterChain(@NotNull ServerHttpSecurity httpSecurity,
                                                      @NotNull ReactiveAuthManager authenticationManager) {
        httpSecurity
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(securityContextRepository)
                .authenticationManager(authenticationManager)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.DELETE).permitAll()
                        .pathMatchers(HttpMethod.POST).permitAll()
                        .pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers(HttpMethod.PUT).permitAll()
                        .pathMatchers("/droneia/api/v1/auth/**").permitAll()
                        .pathMatchers("/api-docs/**", "/api-docs").permitAll()
                        .pathMatchers("/test").hasRole("ADMIN")
                        .pathMatchers("/droneia/api/v1/drone/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .addFilterAt(new JwtAuthWebFilter(jwtService), SecurityWebFiltersOrder.AUTHENTICATION);


        return httpSecurity.build();
    }
}