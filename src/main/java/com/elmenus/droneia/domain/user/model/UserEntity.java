package com.elmenus.droneia.domain.user.model;

import com.elmenus.droneia.infrastructure.security.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.util.annotation.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    @NonNull
    private String username;
    private String password;
    private String fullName;
    private BigDecimal balance;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;


}
