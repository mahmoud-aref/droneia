package com.elmenus.droneia.domain.user.model;

import com.elmenus.droneia.infrastructure.security.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    private UUID id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String fullName;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

}
