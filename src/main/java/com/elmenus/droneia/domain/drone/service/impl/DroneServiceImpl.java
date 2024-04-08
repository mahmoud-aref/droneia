package com.elmenus.droneia.domain.drone.service.impl;

import com.elmenus.droneia.domain.common.BasicResponse;
import com.elmenus.droneia.domain.drone.model.*;
import com.elmenus.droneia.domain.drone.service.DroneService;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
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

    @Override
    public Mono<BasicResponse<DroneEntity>> registerDrone(DroneRegistrationRequest request) {
        return droneRepository.save(droneMapper.toEntity(request))
                .flatMap(savedDrone -> Mono.just(new BasicResponse<>(
                        "Drone registered successfully", savedDrone
                )));
    }

    @Override
    public Mono<BasicResponse<DroneEntity>> updateDrone(DroneUpdateRequest droneEntity) {
        return droneRepository.save(droneMapper.toEntity(droneEntity))
                .flatMap(updatedDrone -> Mono.just(new BasicResponse<>(
                        "Drone updated successfully", updatedDrone
                )));
    }

    @Override
    public Mono<BasicResponse<Void>> deleteDrone(String droneId) {
        // todo : handle cannot delete due to usage
        return droneRepository.deleteById(UUID.fromString(droneId))
                .then(Mono.just(new BasicResponse<>("Drone deleted successfully", null)));
    }

    @Override
    public Mono<BasicResponse<Void>> chargeDrone(String droneId, int batteryPercentage) {
        return droneRepository.findById(UUID.fromString(droneId))
                .map(droneEntity -> {
                    droneEntity.setBatteryPercentage(batteryPercentage);
                    return droneEntity;
                })
                .flatMap(droneRepository::save)
                .then(Mono.just(new BasicResponse<>("Drone charged successfully", null)));
    }

    @Override
    public Mono<BasicResponse<Void>> updateDroneStatus(String droneId, String status) {
        return droneRepository.findById(UUID.fromString(droneId))
                .map(droneEntity -> {
                    droneEntity.setState(DroneState.valueOf(status)); // handle not valid value
                    return droneEntity;
                })
                .flatMap(droneRepository::save)
                .then(Mono.just(new BasicResponse<>("Drone status updated successfully", null)));
    }

    @Override
    public Mono<BasicResponse<DroneEntity>> getDroneData(String droneId) {
        return droneRepository.findById(UUID.fromString(droneId))
                .map(droneEntity -> new BasicResponse<>("Drone data retrieved successfully", droneEntity));
    }

    @Override
    public Flux<BasicResponse<DroneEntity>> getAllDrones() {
        return droneRepository.findAll()
                .map(droneEntity -> new BasicResponse<>("Drone data retrieved successfully", droneEntity));
    }

    @Override
    public Flux<BasicResponse<DroneEntity>> getAllDronesByStatus(String status) {
        return droneRepository.findAllByState(DroneState.valueOf(status)) // handle invalid status
                .map(droneEntity -> new BasicResponse<>("Drone data retrieved successfully", droneEntity));
    }

    @Override
    public Mono<BasicResponse<Void>> loadMedication(String droneId, String medicationId, int quantity) {
        return null;
    }

    @Override
    public Mono<BasicResponse<Void>> unloadMedication(String droneId, String medicationId, int quantity) {
        return null;
    }


}
