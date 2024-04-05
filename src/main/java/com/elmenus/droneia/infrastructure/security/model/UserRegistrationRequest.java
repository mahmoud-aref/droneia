package com.elmenus.droneia.infrastructure.security.model;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String name;
    private String role; // simple implementation for roles
}