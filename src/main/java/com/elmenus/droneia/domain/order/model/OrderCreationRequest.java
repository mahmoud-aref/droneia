package com.elmenus.droneia.domain.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrderCreationRequest {
    private String droneId;
    private List<String> medicationIds;
}
