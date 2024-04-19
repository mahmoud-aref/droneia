package com.elmenus.droneia.domain.order.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationLoad {
    private String medicationId;
    private int medicationQuantity;
}
