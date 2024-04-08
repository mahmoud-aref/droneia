package com.elmenus.droneia.domain.medication.service;

import com.elmenus.droneia.domain.common.BasicResponse;
import com.elmenus.droneia.domain.medication.model.MedicationEntity;
import com.elmenus.droneia.domain.medication.model.MedicationRegistrationRequest;
import com.elmenus.droneia.domain.medication.model.MedicationUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MedicationService {
    Mono<BasicResponse<MedicationEntity>> registerMedication(MedicationRegistrationRequest request);

    Mono<BasicResponse<MedicationEntity>> updateMedication(MedicationUpdateRequest droneEntity);

    Mono<BasicResponse<Void>> deleteMedication(String droneId);

    Mono<BasicResponse<MedicationEntity>> getMedicationData(String droneId);

    Flux<BasicResponse<MedicationEntity>> getAllMedications();

}
