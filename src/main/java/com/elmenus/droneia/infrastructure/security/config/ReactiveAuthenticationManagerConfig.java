package com.elmenus.droneia.infrastructure.security.config;

import com.elmenus.droneia.infrastructure.datasource.sql.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class ReactiveAuthenticationManagerConfig {

    private final UserRepository repository;

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

//    @Bean
//    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
//                                                                       PasswordEncoder passwordEncoder) {
//        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
//        authenticationManager.setPasswordEncoder(passwordEncoder);
//        return authenticationManager;
//    }


}
