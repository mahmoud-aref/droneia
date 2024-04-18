package com.elmenus.droneia.domain.order.service.impl;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.exception.DroneBusyException;
import com.elmenus.droneia.domain.drone.exception.LowBatteryException;
import com.elmenus.droneia.domain.drone.model.DroneState;
import com.elmenus.droneia.domain.order.model.MedicationLoad;
import com.elmenus.droneia.domain.order.model.OrderLoadingRequest;
import com.elmenus.droneia.domain.order.model.OrderEntity;
import com.elmenus.droneia.domain.order.model.OrderStatus;
import com.elmenus.droneia.domain.order.service.OrderService;
import com.elmenus.droneia.domain.order.validation.ValidLoad;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.medication.MedicationRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @Override
    public Mono<BasicResponse<OrderEntity>> createOrder(@ValidLoad OrderLoadingRequest request) {
        return droneRepository
                .findById(UUID.fromString(request.getDroneId()))
                .flatMap(droneEntity -> {
                    if (droneEntity.getBatteryPercentage() < 25) {
                        return Mono.error(new LowBatteryException(BATTERY_LOW_EXCEPTION_MESSAGE));
                    } else if (!DroneState.IDLE.equals(droneEntity.getState())) {
                        return Mono.error(new DroneBusyException(DRONE_IS_BUSY_EXCEPTION));
                    } else {
                        return medicationRepository.findAllById(
                                        request.getMedicationItems()
                                                .stream()
                                                .map(MedicationLoad::getMedicationId)
                                                .map(UUID::fromString)
                                                .toList()
                                ).collect(toSet())
                                .map(medications -> OrderEntity.builder()
                                        .id(UUID.randomUUID())
                                        .drone(droneEntity)
                                        .medications(medications)
                                        .build())
                                .flatMap(orderRepository::save)
                                .flatMap(order -> Mono.just(new BasicResponse<>(ORDER_CREATED_SUCCESSFULLY, order)));
                    }
                });
    }

    @Override
    public Mono<BasicResponse<OrderEntity>> getOrderById(String orderId) {
        return orderRepository.findById(UUID.fromString(orderId))
                .flatMap(order -> Mono.just(new BasicResponse<>(ORDER_RETRIEVED_SUCCESSFULLY, order)));
    }

    @Override
    public Mono<BasicResponse<OrderEntity>> getOrderOfDrone(String droneId) {
        return orderRepository.findByDroneId(UUID.fromString(droneId))
                .flatMap(order -> Mono.just(new BasicResponse<>(ORDER_RETRIEVED_SUCCESSFULLY, order)));
    }

    @Override
    public Flux<BasicResponse<OrderEntity>> getAllOrders() {
        return orderRepository.findAll().map(
                orderEntity -> new BasicResponse<>(ORDER_RETRIEVED_SUCCESSFULLY, orderEntity)
        );
    }

    @Override
    public Flux<BasicResponse<OrderEntity>> getAllActiveOrders() {
        return orderRepository.findAllByStatus(OrderStatus.ACTIVE).map(
                orderEntity -> new BasicResponse<>(ORDER_RETRIEVED_SUCCESSFULLY, orderEntity)
        );
    }

}
