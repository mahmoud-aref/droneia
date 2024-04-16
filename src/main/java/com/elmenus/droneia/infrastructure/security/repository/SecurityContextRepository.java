package com.elmenus.droneia.infrastructure.security.repository;

import com.elmenus.droneia.infrastructure.security.config.ReactiveAuthManager;
import com.elmenus.droneia.infrastructure.security.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private static final String BEARER_PREFIX = "Bearer ";
    private final ReactiveAuthManager authenticationManager;
    private final JwtService jwtService;

    // we're not using stateful sessions, so we don't need to save the SecurityContext
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest())
                .mapNotNull(serverHttpRequest -> serverHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authenticationHeader -> authenticationHeader != null && authenticationHeader.startsWith(BEARER_PREFIX))
                .switchIfEmpty(Mono.empty())
                .map(authHeader -> authHeader.replace(BEARER_PREFIX, "".trim()))
                .flatMap(authToken -> authenticationManager.authenticate(jwtService.getAuthentication(authToken)))
                .map(SecurityContextImpl::new);
    }

}
