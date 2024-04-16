package com.elmenus.droneia.infrastructure.security.filter;

import com.elmenus.droneia.infrastructure.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtAuthWebFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange,
                                      @NonNull WebFilterChain chain) {

        var authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String token = jwtService.getTokenFromHeader(authHeaders.toString());
            return ReactiveSecurityContextHolder
                    .getContext()
                    .doOnNext(securityContext -> {
                        if (token != null && jwtService.getUsernameFromToken(token) != null) {
                            var auth = jwtService.getAuthentication(token);
                            securityContext.setAuthentication(auth);
                        }
                    }).then(chain.filter(exchange));
        }
        return chain.filter(exchange);
    }


}
