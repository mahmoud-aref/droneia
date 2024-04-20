package com.elmenus.droneia.helper;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.medication.model.MedicationEntity;
import com.elmenus.droneia.domain.medication.model.MedicationRegistrationRequest;
import com.fasterxml.jackson.core.type.TypeReference;

public class MedicationTestingHelper {

    public static MedicationRegistrationRequest getMockMedicationRegistrationRequest() {
        return MedicationRegistrationRequest.builder()
                .name("Medication-A")
                .code("CODE_A")
                .imageUrl("https://www.google.com")
                .weight(1.0)
                .build();
    }

    public static class MedicationEntityTypeReference extends TypeReference<BasicResponse<MedicationEntity>> {
    }
}
