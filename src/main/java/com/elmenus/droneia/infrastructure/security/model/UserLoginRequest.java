package com.elmenus.droneia.infrastructure.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
    private String username;
    private String password;
}