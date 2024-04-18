package com.elmenus.droneia.infrastructure.datasource.sql.order;

import com.elmenus.droneia.domain.order.model.OrderMedRef;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface OrderMedicationRepository extends R2dbcRepository<OrderMedRef, UUID> {
}
