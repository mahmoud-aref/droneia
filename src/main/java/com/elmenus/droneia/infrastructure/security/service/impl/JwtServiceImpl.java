package com.elmenus.droneia.infrastructure.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.elmenus.droneia.infrastructure.security.exceptions.UnauthorizedException;
import com.elmenus.droneia.infrastructure.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    @Override
    public String getUsernameFromToken(String token) {
        return validateTokenAndReturnSubject(token);
    }

    @Override
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        String authorities = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_ANONYMOUS");

        Algorithm algorithm = Algorithm.HMAC256(this.jwtSigningKey);

        return JWT.create()
                .withIssuer("droneia")
                .withSubject(username)
                .withClaim("roles", authorities)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .sign(algorithm);
    }

    @Override
    public Authentication getAuthentication(String token) {
        DecodedJWT claims = getPayload(token);

        var roles = claims.getClaim("roles");

        var authorities = roles == null
                ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


    private String validateTokenAndReturnSubject(String token) {
        try {
            DecodedJWT decodedJWT = getPayload(token);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    private DecodedJWT getPayload(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSigningKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}