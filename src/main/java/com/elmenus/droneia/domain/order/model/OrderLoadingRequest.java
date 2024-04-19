package com.elmenus.droneia.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLoadingRequest {
    private String droneId;
    private List<MedicationLoad> medicationItems;
}
