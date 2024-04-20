package com.elmenus.droneia.factory;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneModel;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.UUID;

public class DroneTestingHelper {

    public static DroneRegistrationRequest getMockDroneRegistrationRequest() {
        return DroneRegistrationRequest.builder()
                .serialNumber(UUID.randomUUID().toString())
                .model(DroneModel.LIGHT_WEIGHT)
                .maxWeight(100)
                .build();
    }

    public static class DroneEntityTypeReference extends TypeReference<BasicResponse<DroneEntity>> {
    }
    // Oh ... s*t ,here we go again, another class with a nested class that extends TypeReference
}
