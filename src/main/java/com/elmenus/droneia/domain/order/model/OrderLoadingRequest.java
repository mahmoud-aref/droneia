package com.elmenus.droneia.domain.order.model;

import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;

@Builder
@Data
public class OrderLoadingRequest {
    private String droneId;
    private Flux<MedicationLoad> medicationItems;
}
