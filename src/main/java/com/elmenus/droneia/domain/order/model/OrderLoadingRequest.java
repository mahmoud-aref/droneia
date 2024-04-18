package com.elmenus.droneia.domain.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrderLoadingRequest {
    private String droneId;
    private List<MedicationLoad> medicationItems;
}
