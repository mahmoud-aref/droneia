package com.elmenus.droneia.domain.drone.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DroneRegistrationRequest {
    @Size(max = 100)
    private String serialNumber;
    private String model;
    @Max(500)
    private double maxWeight;
}
