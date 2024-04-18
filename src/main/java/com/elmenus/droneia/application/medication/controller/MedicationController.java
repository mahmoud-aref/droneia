package com.elmenus.droneia.application.medication.controller;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.medication.model.MedicationEntity;
import com.elmenus.droneia.domain.medication.model.MedicationRegistrationRequest;
import com.elmenus.droneia.domain.medication.model.MedicationUpdateRequest;
import com.elmenus.droneia.domain.medication.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(MedicationController.DRONE_PATH)
@RequiredArgsConstructor
public class MedicationController {

    public static final String DRONE_PATH = "/droneia/api/v1/medication";
    public static final String DRONE_PATH_REGISTER = "/register";
    public static final String DRONE_PATH_UPDATE = "/update";
    public static final String DRONE_PATH_DELETE = "/delete/{id}";
    public static final String DRONE_PATH_GET = "/get/{id}";
    public static final String DRONE_PATH_GET_ALL = "/get/all";

    private final MedicationService medicationService;

    @PostMapping(DRONE_PATH_REGISTER)
    public Mono<ResponseEntity<BasicResponse<MedicationEntity>>> registerMedication(@Valid @RequestBody Mono<MedicationRegistrationRequest> monoRequest) {
        return monoRequest.flatMap(
                request -> medicationService.registerMedication(request)
                        .map(ResponseEntity::ok)
        );
    }

    @PutMapping(DRONE_PATH_UPDATE)
    public Mono<ResponseEntity<BasicResponse<MedicationEntity>>> updateMedication(@Valid @RequestBody Mono<MedicationUpdateRequest> monoRequest) {
        return monoRequest.flatMap(
                request -> medicationService.updateMedication(request)
                        .map(ResponseEntity::ok)
        );
    }

    @DeleteMapping(DRONE_PATH_DELETE)
    public Mono<ResponseEntity<BasicResponse<Void>>> deleteMedication(@PathVariable String id) {
        return medicationService.deleteMedication(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping(DRONE_PATH_GET)
    public Mono<ResponseEntity<BasicResponse<MedicationEntity>>> getMedicationData(@PathVariable String id) {
        return medicationService.getMedicationData(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping(DRONE_PATH_GET_ALL)
    public Flux<BasicResponse<MedicationEntity>> getAllMedications() {
        return medicationService.getAllMedications();
    }

}
