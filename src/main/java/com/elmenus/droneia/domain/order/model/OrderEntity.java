package com.elmenus.droneia.domain.order.model;

import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.medication.model.MedicationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class OrderEntity {

    @Id
    @org.springframework.data.annotation.Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private DroneEntity drone;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @ManyToMany
    @JoinTable(name = "order_medications",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private Set<MedicationEntity> medications;
}
