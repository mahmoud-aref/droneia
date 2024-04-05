package com.elmenus.droneia.application;


import com.elmenus.droneia.infrastructure.security.model.JWTResponse;
import com.elmenus.droneia.infrastructure.security.model.UserLoginRequest;
import com.elmenus.droneia.infrastructure.security.model.UserRegistrationRequest;
import com.elmenus.droneia.infrastructure.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UserAuthenticationController.USER_AUTHENTICATION_PATH)
@RequiredArgsConstructor
public class UserAuthenticationController {

    public static final String USER_AUTHENTICATION_PATH = "/droneia/api/v1/auth";
    public static final String USER_REGISTRATION_PATH_REGISTER = "/register";
    public static final String USER_REGISTRATION_PATH_LOGIN = "/login";

    private final AuthenticationService authenticationService;

    @PostMapping(USER_REGISTRATION_PATH_REGISTER)
    public Mono<ResponseEntity<JWTResponse>> registerUser(@RequestBody Mono<UserRegistrationRequest> monoRequest) {
        return monoRequest.flatMap(
                request -> authenticationService.registerUser(request)
                        .map(ResponseEntity::ok)
        );
    }

    @PostMapping(USER_REGISTRATION_PATH_LOGIN)
    public Mono<ResponseEntity<JWTResponse>> authenticateUser(@RequestBody Mono<UserLoginRequest> monoRequest) {
        return monoRequest.flatMap(
                request -> authenticationService.authenticateUser(request)
                        .map(ResponseEntity::ok)
        );
    }

}
