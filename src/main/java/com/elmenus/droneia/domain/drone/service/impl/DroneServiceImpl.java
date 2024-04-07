package com.elmenus.droneia.domain.drone.service.impl;

import com.elmenus.droneia.domain.common.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneMapper;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import com.elmenus.droneia.domain.drone.service.DroneService;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

}
