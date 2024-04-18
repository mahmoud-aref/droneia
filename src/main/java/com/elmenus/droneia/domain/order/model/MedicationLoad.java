package com.elmenus.droneia.domain.order.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MedicationLoad {
    private String medicationId;
    private long medicationQuantity;
}
