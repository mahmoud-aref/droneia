package com.elmenus.droneia.application.drone.controller;

import com.elmenus.droneia.domain.common.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import com.elmenus.droneia.domain.drone.service.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(DroneController.DRONE_PATH)
@RequiredArgsConstructor

public class DroneController {

    public static final String DRONE_PATH = "/droneia/api/v1/drone";
    public static final String DRONE_PATH_REGISTER = "/register";

    private final DroneService droneService;

    @PostMapping(DRONE_PATH_REGISTER)
    public Mono<ResponseEntity<BasicResponse<DroneEntity>>> registerDrone(@Valid @RequestBody Mono<DroneRegistrationRequest> monoRequest) {
        return monoRequest.flatMap(
                request -> droneService.registerDrone(request)
                        .map(ResponseEntity::ok)
        );
    }


}
