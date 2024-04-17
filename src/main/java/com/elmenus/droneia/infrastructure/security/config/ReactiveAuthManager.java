package com.elmenus.droneia.infrastructure.security.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactiveAuthManager extends UserDetailsRepositoryReactiveAuthenticationManager {

    private final PasswordEncoder passwordEncoder;

    public ReactiveAuthManager(ReactiveUserDetailsService userDetailsService,
                               PasswordEncoder passwordEncoder) {
        super(userDetailsService);
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(this.passwordEncoder);
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return super.authenticate(authentication);
    }
}
