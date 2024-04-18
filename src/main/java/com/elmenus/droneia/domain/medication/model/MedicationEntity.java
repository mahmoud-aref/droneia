package com.elmenus.droneia.domain.medication.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medications")
public class MedicationEntity {

    @Id
    private UUID id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name should contain only letters, numbers, _ , and -.")
    private String name;

    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Code should contain only capital letters, numbers, and _.")
    private String code;

    private String imageUrl;
}
