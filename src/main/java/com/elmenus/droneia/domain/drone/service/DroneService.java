package com.elmenus.droneia.domain.drone.service;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import com.elmenus.droneia.domain.drone.model.DroneUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface DroneService {

    // easy for test , in prod can be moved to localization provider
    String DRONE_REGISTERED_SUCCESSFULLY = "Drone registered successfully";
    String DRONE_UPDATED_SUCCESSFULLY = "Drone updated successfully";
    String DRONE_DELETED_SUCCESSFULLY = "Drone deleted successfully";
    String DRONE_CHARGED_SUCCESSFULLY = "Drone charged successfully";
    String DRONE_STATUS_UPDATED_SUCCESSFULLY = "Drone status updated successfully";
    String DRONE_DATA_RETRIEVED_SUCCESSFULLY = "Drone data retrieved successfully";

    Mono<BasicResponse<DroneEntity>> registerDrone(DroneRegistrationRequest request);

    Mono<BasicResponse<DroneEntity>> updateDrone(DroneUpdateRequest droneEntity);

    Mono<BasicResponse<Void>> deleteDrone(String droneId);

    Mono<BasicResponse<Void>> chargeDrone(String droneId, int batteryPercentage);

    Mono<BasicResponse<Void>> updateDroneStatus(String droneId, String status);

    Mono<BasicResponse<DroneEntity>> getDroneData(String droneId);

    Flux<BasicResponse<DroneEntity>> getAllDrones();

    Flux<BasicResponse<DroneEntity>> getAllDronesByStatus(String status);

    Mono<BasicResponse<Void>> loadMedication(String droneId, String medicationId, int quantity);

    Mono<BasicResponse<Void>> unloadMedication(String droneId, String medicationId, int quantity);
}
