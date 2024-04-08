package com.elmenus.droneia.domain.drone.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DroneUpdateRequest {
    private String id;
    @Size(max = 100)
    private String serialNumber;
    private String model;
    @Max(500)
    private double maxWeight;
}
