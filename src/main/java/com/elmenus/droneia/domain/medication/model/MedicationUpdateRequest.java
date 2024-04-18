package com.elmenus.droneia.domain.medication.model;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MedicationUpdateRequest {
    private String id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name should contain only letters, numbers, _ , and -.")
    private String name;

    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Code should contain only capital letters, numbers, and _.")
    private String code;
    private String imageUrl;
}
