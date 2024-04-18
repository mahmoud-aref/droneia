package com.elmenus.droneia.domain.drone.service.impl;

import com.elmenus.droneia.domain.common.exception.ResourceNotFoundException;
import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.exception.DroneBusyException;
import com.elmenus.droneia.domain.drone.model.*;
import com.elmenus.droneia.domain.drone.service.DroneService;
import com.elmenus.droneia.domain.order.model.OrderStatus;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.order.OrderRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneMapper droneMapper;
    private final DroneRepository droneRepository;
    private final OrderRepository orderRepository;

    @Override
    public Mono<BasicResponse<DroneEntity>> registerDrone(@Valid DroneRegistrationRequest request) {
        return droneRepository.save(droneMapper.toEntity(request))
                .flatMap(savedDrone -> Mono.just(new BasicResponse<>(
                        DRONE_REGISTERED_SUCCESSFULLY, savedDrone
                )));
    }

    @Override
    public Mono<BasicResponse<DroneEntity>> updateDrone(DroneUpdateRequest droneUpdateRequest) {
        return droneRepository.findById(UUID.fromString(droneUpdateRequest.getId()))
                .map(existingDrone -> droneMapper.toEntity(droneUpdateRequest, existingDrone))
                .flatMap(droneRepository::save)
                .flatMap(updatedDrone -> Mono.just(new BasicResponse<>(
                        DRONE_UPDATED_SUCCESSFULLY, updatedDrone
                )));
    }

    @Override
    public Mono<BasicResponse<Void>> deleteDrone(String droneId) {
        return droneRepository
                .findById(UUID.fromString(droneId))
                .flatMap(droneEntity -> {
                    if (!DroneState.IDLE.equals(droneEntity.getState())) {
                        return Mono.error(new DroneBusyException(CANNOT_DELETE_DUE_TO_BUSY));
                    } else {
                        return droneRepository.deleteById(UUID.fromString(droneId))
                                .then(Mono.just(new BasicResponse<>(DRONE_DELETED_SUCCESSFULLY, null)));
                    }
                });
    }

    @Override
    public Mono<BasicResponse<Void>> chargeDrone(String droneId, int batteryPercentage) {
        return droneRepository.findById(UUID.fromString(droneId))
                .map(droneEntity -> {
                    droneEntity.setBatteryPercentage(batteryPercentage);
                    return droneEntity;
                })
                .flatMap(droneRepository::save)
                .then(Mono.just(new BasicResponse<>(DRONE_CHARGED_SUCCESSFULLY, null)));
    }

    @Override
    public Mono<BasicResponse<Void>> updateDroneStatus(String droneId, String status) {
        return droneRepository.findById(UUID.fromString(droneId))
                .map(droneEntity -> {
                    if (DroneState.DELIVERED.toString().equals(status)) {
                        // if drone is delivered we need to change order to finish
                        orderRepository.findByDroneAndStatus(droneEntity, OrderStatus.FINISHED)
                                .flatMap(orderEntity -> {
                                    orderEntity.setStatus(OrderStatus.FINISHED);
                                    return orderRepository.save(orderEntity);
                                }); // no need to wait
                    }
                    droneEntity.setState(DroneState.valueOf(status));
                    return droneEntity;
                }).flatMap(droneRepository::save)
                .flatMap(droneEntity -> Mono.just(new BasicResponse<>(DRONE_STATUS_UPDATED_SUCCESSFULLY, null)));
    }

    @Override
    public Mono<BasicResponse<DroneEntity>> getDroneData(String droneId) {
        return droneRepository.findById(UUID.fromString(droneId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Drone not found")))
                .map(droneEntity -> new BasicResponse<>(DRONE_DATA_RETRIEVED_SUCCESSFULLY, droneEntity));
    }

    @Override
    public Flux<BasicResponse<DroneEntity>> getAllDrones() {
        return droneRepository.findAll()
                .map(droneEntity -> new BasicResponse<>(DRONE_DATA_RETRIEVED_SUCCESSFULLY, droneEntity));
    }

    @Override
    public Flux<BasicResponse<DroneEntity>> getAllDronesByStatus(String status) {
        return droneRepository.findAllByState(DroneState.valueOf(status)) // handle invalid status
                .map(droneEntity -> new BasicResponse<>(DRONE_DATA_RETRIEVED_SUCCESSFULLY, droneEntity));
    }

    @Override
    public Flux<BasicResponse<DroneEntity>> getAvailableDronesForLoading() {
        return droneRepository.findAllByState(DroneState.IDLE)
                .map(droneEntity -> new BasicResponse<>(DRONE_DATA_RETRIEVED_SUCCESSFULLY, droneEntity));
    }

}
