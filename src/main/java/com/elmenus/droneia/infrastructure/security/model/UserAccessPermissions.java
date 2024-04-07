package com.elmenus.droneia.infrastructure.security.model;

public interface UserAccessPermissions {
    String HAS_ADMIN_ROLE = "hasAnyAuthority('ROLE_ADMIN')";
    String HAS_USER_ROLE = "hasAnyAuthority('ROLE_USER')";
}
