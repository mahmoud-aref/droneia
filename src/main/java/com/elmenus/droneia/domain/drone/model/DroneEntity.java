package com.elmenus.droneia.domain.drone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
    private UUID id;

    @Size(max = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Size(max = 500)
    private double maxWeight;

    @Size(max = 100)
    private int batteryPercentage;

    @Enumerated(EnumType.STRING)
    private DroneState state;

}
