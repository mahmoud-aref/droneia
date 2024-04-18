package com.elmenus.droneia.domain.drone.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drones")
public class DroneEntity {

    @Id
    private UUID id;

    @Size(max = 100)
    private String serialNumber;

    @NotBlank
    private DroneModel model;

    @Max(500)
    private double maxWeight;

    @Max(100)
    @Builder.Default
    private int batteryPercentage = 100;

    @Builder.Default
    private DroneState state = DroneState.IDLE;

    // toString - the Autistic toString of lombok is adding class name and no way to remove it :)
    @Override
    public String toString() {
        return "{id=" + this.getId() +
                ", serialNumber=" + this.getSerialNumber() +
                ", model=" + this.getModel() +
                ", maxWeight=" + this.getMaxWeight() +
                ", batteryPercentage=" + this.getBatteryPercentage() +
                ", state=" + this.getState() + "}";
    }
}
