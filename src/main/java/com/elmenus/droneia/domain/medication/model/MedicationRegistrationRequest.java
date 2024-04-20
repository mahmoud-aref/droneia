package com.elmenus.droneia.domain.medication.model;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MedicationRegistrationRequest {
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name should contain only letters, numbers, _ , and -.")
    private String name;

    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Code should contain only capital letters, numbers, and _.")
    private String code;
    // this should be fil of multipart,
    // but I had no time to fix it and make it work
    private String imageUrl;
}
