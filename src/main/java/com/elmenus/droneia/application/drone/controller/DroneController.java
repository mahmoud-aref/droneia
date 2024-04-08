package com.elmenus.droneia.application.drone.controller;

import com.elmenus.droneia.domain.common.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import com.elmenus.droneia.domain.drone.model.DroneUpdateRequest;
import com.elmenus.droneia.domain.drone.service.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(DroneController.DRONE_PATH)
@RequiredArgsConstructor
public class DroneController {

    public static final String DRONE_PATH = "/droneia/api/v1/drone";
    public static final String DRONE_PATH_REGISTER = "/register";
    public static final String DRONE_PATH_UPDATE = "/update";
    public static final String DRONE_PATH_DELETE = "/delete";
    public static final String DRONE_PATH_GET = "/get/{id}";
    public static final String DRONE_PATH_GET_ALL = "/get/all";
    public static final String DRONE_PATH_GET_ALL_BY_STATUS = "/get/all/{status}";
    public static final String DRONE_PATH_CHARGE = "/charge/{id}/{batteryPercentage}";
    public static final String DRONE_PATH_UPDATE_STATUS = "/update/status/{id}/{status}";


    private final DroneService droneService;

    @PostMapping(DRONE_PATH_REGISTER)
    public Mono<ResponseEntity<BasicResponse<DroneEntity>>> registerDrone(@Valid @RequestBody Mono<DroneRegistrationRequest> monoRequest) {
        return monoRequest.flatMap(
                request -> droneService.registerDrone(request)
                        .map(ResponseEntity::ok)
        );
    }

    @PutMapping(DRONE_PATH_UPDATE)
    public Mono<ResponseEntity<BasicResponse<DroneEntity>>> updateDrone(@Valid @RequestBody Mono<DroneUpdateRequest> monoRequest) {
        return monoRequest.flatMap(
                request -> droneService.updateDrone(request)
                        .map(ResponseEntity::ok)
        );
    }

    @DeleteMapping(DRONE_PATH_DELETE)
    public Mono<ResponseEntity<BasicResponse<Void>>> deleteDrone(@PathVariable String id) {
        return droneService.deleteDrone(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping(DRONE_PATH_GET)
    public Mono<ResponseEntity<BasicResponse<DroneEntity>>> getDroneData(@PathVariable String id) {
        return droneService.getDroneData(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping(DRONE_PATH_GET_ALL)
    public Flux<ResponseEntity<BasicResponse<DroneEntity>>> getAllDrones() {
        return droneService.getAllDrones()
                .map(ResponseEntity::ok);
    }

    @GetMapping(DRONE_PATH_GET_ALL_BY_STATUS)
    public Flux<ResponseEntity<BasicResponse<DroneEntity>>> getAllDronesByStatus(@PathVariable String status) {
        return droneService.getAllDronesByStatus(status)
                .map(ResponseEntity::ok);
    }

    @PutMapping(DRONE_PATH_CHARGE)
    public Mono<ResponseEntity<BasicResponse<Void>>> chargeDrone(@PathVariable String id, @PathVariable int batteryPercentage) {
        return droneService.chargeDrone(id, batteryPercentage)
                .map(ResponseEntity::ok);
    }

    @PutMapping(DRONE_PATH_UPDATE_STATUS)
    public Mono<ResponseEntity<BasicResponse<Void>>> updateDroneStatus(@PathVariable String id, @PathVariable String status) {
        return droneService.updateDroneStatus(id, status)
                .map(ResponseEntity::ok);
    }

}
