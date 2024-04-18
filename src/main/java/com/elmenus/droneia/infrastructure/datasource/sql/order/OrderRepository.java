package com.elmenus.droneia.infrastructure.datasource.sql.order;

import com.elmenus.droneia.domain.order.model.OrderEntity;
import com.elmenus.droneia.domain.order.model.OrderStatus;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderRepository extends R2dbcRepository<OrderEntity, UUID> {
    Mono<OrderEntity> findByDroneId(String droneId);

    Flux<OrderEntity> findAllByStatus(OrderStatus orderStatus);

    Mono<OrderEntity> findByDroneIdAndStatus(String droneId, OrderStatus orderStatus);
}
