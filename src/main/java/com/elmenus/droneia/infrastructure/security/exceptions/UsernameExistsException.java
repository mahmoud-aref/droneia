package com.elmenus.droneia.infrastructure.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username already exists")
public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super("Username already exists");
    }
}
