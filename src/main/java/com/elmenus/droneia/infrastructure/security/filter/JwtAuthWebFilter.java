package com.elmenus.droneia.infrastructure.security.filter;

import com.elmenus.droneia.infrastructure.security.exceptions.UnauthorizedException;
import com.elmenus.droneia.infrastructure.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
public class JwtAuthWebFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange,
                                      @NonNull WebFilterChain chain) {

        String token = getTokenFromHeader(exchange.getRequest().getHeaders());
        if (token != null && jwtService.getUsernameFromToken(token) != null) {
            return Mono.fromCallable(
                            () -> jwtService.getAuthentication(token))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(authentication -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));

        } // this is a workaround until fix loading the authentication context correctly
        throw new UnauthorizedException("Unauthorized");
    }

    private String getTokenFromHeader(HttpHeaders headers) {
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
