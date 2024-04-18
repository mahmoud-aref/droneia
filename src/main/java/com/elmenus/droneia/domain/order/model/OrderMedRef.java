package com.elmenus.droneia.domain.order.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table(name = "order_medications")
public class OrderMedRef {
    @Id
    private UUID id;
    private UUID orderId;
    private UUID medicationId;
    private int quantity;
}
