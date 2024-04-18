package com.elmenus.droneia.domain.order.service.impl;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.exception.DroneBusyException;
import com.elmenus.droneia.domain.drone.exception.LowBatteryException;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneState;
import com.elmenus.droneia.domain.order.model.OrderEntity;
import com.elmenus.droneia.domain.order.model.OrderLoadingRequest;
import com.elmenus.droneia.domain.order.model.OrderMedRef;
import com.elmenus.droneia.domain.order.model.OrderStatus;
import com.elmenus.droneia.domain.order.service.OrderService;
import com.elmenus.droneia.domain.order.validation.ValidLoad;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.order.OrderMedicationRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DroneRepository droneRepository;
    private final OrderMedicationRepository orderMedicationRepository;


    @Override
    public Mono<BasicResponse<OrderEntity>> createOrder(@ValidLoad OrderLoadingRequest request) {
        AtomicReference<DroneEntity> workingDrone = new AtomicReference<>();
        AtomicReference<OrderEntity> workingOrder = new AtomicReference<>();
        return droneRepository
                .findById(UUID.fromString(request.getDroneId()))
                .filter(drone -> drone.getBatteryPercentage() >= 25)
                .switchIfEmpty(Mono.error(new LowBatteryException(BATTERY_LOW_EXCEPTION_MESSAGE)))
                .filter(drone -> DroneState.IDLE.equals(drone.getState()))
                .switchIfEmpty(Mono.error(new DroneBusyException(DRONE_IS_BUSY_EXCEPTION)))
                .flatMap(drone -> {
                    workingDrone.set(drone);
                    drone.setState(DroneState.LOADING);
                    return droneRepository.save(drone);
                })
                .flatMap(drone -> {
                    var order = OrderEntity.builder()
                            .droneId(drone.getId().toString())
                            .build();
                    return orderRepository.save(order);
                }).map(order -> {
                    workingOrder.set(order);
                    return request.getMedicationItems()
                            .flatMap(load -> {
                                var orderMedRef = OrderMedRef.builder()
                                        .orderId(order.getId())
                                        .medicationId(UUID.fromString(load.getMedicationId()))
                                        .quantity(load.getMedicationQuantity())
                                        .build();
                                return orderMedicationRepository.save(orderMedRef);
                            });
                })
                .then(droneRepository.save(finalizeDroneState(workingDrone.get())))
                .thenReturn(new BasicResponse<>(ORDER_CREATED_SUCCESSFULLY, workingOrder.get()));
    }

    private DroneEntity finalizeDroneState(DroneEntity drone) {
        drone.setState(DroneState.LOADED);
        return drone;
    }

    @Override
    public Mono<BasicResponse<OrderEntity>> getOrderById(String orderId) {
        return orderRepository.findById(UUID.fromString(orderId))
                .flatMap(order -> Mono.just(new BasicResponse<>(ORDER_RETRIEVED_SUCCESSFULLY, order)));
    }

    @Override
    public Mono<BasicResponse<OrderEntity>> getOrderOfDrone(String droneId) {
        return orderRepository.findByDroneId(droneId)
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
