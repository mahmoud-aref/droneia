package com.elmenus.droneia.domain.user.model;

import com.elmenus.droneia.infrastructure.security.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String fullName;

    private boolean active;

    private Role role;

}
