package com.elmenus.droneia.domain.order.service.impl;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.exception.DroneBusyException;
import com.elmenus.droneia.domain.drone.exception.LowBatteryException;
import com.elmenus.droneia.domain.drone.model.DroneState;
import com.elmenus.droneia.domain.order.model.*;
import com.elmenus.droneia.domain.order.service.OrderService;
import com.elmenus.droneia.domain.order.validation.ValidLoad;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.order.OrderMedicationRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DroneRepository droneRepository;
    private final OrderMedicationRepository orderMedicationRepository;

    @Override
    @Transactional
    public Mono<BasicResponse<OrderResponse>> createOrder(@ValidLoad OrderLoadingRequest request) {
        OrderResponse response = new OrderResponse();
        return Mono.from(droneRepository
                .findById(UUID.fromString(request.getDroneId()))
                .filter(drone -> drone.getBatteryPercentage() >= 25)
                .switchIfEmpty(Mono.error(new LowBatteryException(BATTERY_LOW_EXCEPTION_MESSAGE)))
                .filter(drone -> DroneState.IDLE.equals(drone.getState()))
                .switchIfEmpty(Mono.error(new DroneBusyException(DRONE_IS_BUSY_EXCEPTION)))
                .flatMap(drone -> {
                    response.setDroneId(String.valueOf(drone.getId()));
                    drone.setState(DroneState.LOADING);
                    return droneRepository.save(drone);
                })
                .flatMap(drone -> {
                    var order = OrderEntity.builder()
                            .droneId(drone.getId())
                            .build();
                    return orderRepository.save(order)
                            .map(savedOrder -> Tuples.of(savedOrder, drone));
                }).map(tupleOfOrderDrone -> {
                    response.setOrderId(String.valueOf(tupleOfOrderDrone.getT1().getId()));
                    var orderRefs = request.getMedicationItems()
                            .stream()
                            .map(load -> OrderMedRef.builder()
                                    .orderId(tupleOfOrderDrone.getT1().getId())
                                    .medicationId(UUID.fromString(load.getMedicationId()))
                                    .quantity(load.getMedicationQuantity())
                                    .build()).toList();
                    return orderMedicationRepository.saveAll(orderRefs)
                            .map(ignore -> Tuples.of(tupleOfOrderDrone.getT1(), tupleOfOrderDrone.getT2()));
                })
                .flatMapMany(Flux::firstWithValue)
                .flatMap(tupleOfOrderDrone -> {
                    var droneFromTuple = tupleOfOrderDrone.getT2();
                    droneFromTuple.setState(DroneState.LOADED);
                    return droneRepository.save(droneFromTuple);
                })
                .flatMap(ignore -> Mono.just(new BasicResponse<>(DRONE_LOADED_SUCCESSFULLY, response))));
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
        return orderRepository.findAllByState(OrderState.ACTIVE).map(
                orderEntity -> new BasicResponse<>(ORDER_RETRIEVED_SUCCESSFULLY, orderEntity)
        );
    }


}
