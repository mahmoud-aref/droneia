package com.elmenus.droneia.domain.drone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drones")
@Entity
public class DroneEntity {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Size(max = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private DroneModel model;

    @Max(500)
    private double maxWeight;

    @Max(100)
    @Builder.Default
    private int batteryPercentage = 100;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DroneState state = DroneState.IDLE;

}
