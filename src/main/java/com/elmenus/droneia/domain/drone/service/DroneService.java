package com.elmenus.droneia.domain.drone.service;

import com.elmenus.droneia.domain.common.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import reactor.core.publisher.Mono;

public interface DroneService {

    Mono<BasicResponse<DroneEntity>> registerDrone(DroneRegistrationRequest request);

}
