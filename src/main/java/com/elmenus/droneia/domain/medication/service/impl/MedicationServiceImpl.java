package com.elmenus.droneia.domain.medication.service.impl;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.medication.model.MedicationEntity;
import com.elmenus.droneia.domain.medication.model.MedicationMapper;
import com.elmenus.droneia.domain.medication.model.MedicationRegistrationRequest;
import com.elmenus.droneia.domain.medication.model.MedicationUpdateRequest;
import com.elmenus.droneia.domain.medication.service.MedicationService;
import com.elmenus.droneia.infrastructure.datasource.sql.medication.MedicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Override
    public Mono<BasicResponse<MedicationEntity>> registerMedication(MedicationRegistrationRequest request) {
        return medicationRepository.save(medicationMapper.toEntity(request))
                .flatMap(savedMedication -> Mono.just(new BasicResponse<>(
                        "Medication registered successfully", savedMedication
                )));
    }

    @Override
    public Mono<BasicResponse<MedicationEntity>> updateMedication(MedicationUpdateRequest medicationUpdateRequest) {
        return medicationRepository.findById(UUID.fromString(medicationUpdateRequest.getId()))
                .map(existingDrone -> medicationMapper.toEntity(medicationUpdateRequest, existingDrone))
                .flatMap(medicationRepository::save)
                .flatMap(updatedDrone -> Mono.just(new BasicResponse<>(
                        "Medication updated successfully", updatedDrone
                )));
    }

    @Override
    public Mono<BasicResponse<Void>> deleteMedication(String droneId) {
        // todo : handle cannot delete due to usage
        return medicationRepository.deleteById(UUID.fromString(droneId))
                .then(Mono.just(new BasicResponse<>("Medication deleted successfully", null)));
    }

    @Override
    public Mono<BasicResponse<MedicationEntity>> getMedicationData(String droneId) {
        return medicationRepository.findById(UUID.fromString(droneId))
                .flatMap(med -> Mono.just(new BasicResponse<>(
                        "Medication data retrieved successfully", med
                )));
    }

    @Override
    public Flux<BasicResponse<MedicationEntity>> getAllMedications() {
        return medicationRepository.findAll()
                .map(med -> new BasicResponse<>("Medication data retrieved successfully", med));
    }
}
