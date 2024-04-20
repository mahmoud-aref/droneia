package com.elmenus.droneia.helper;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.model.*;
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

    public static DroneUpdateRequest getMockLowBatteryDroneUpdateRequest(String droneId) {
        return DroneUpdateRequest.builder()
                .id(droneId)
                .serialNumber(UUID.randomUUID().toString())
                .model(DroneModel.LIGHT_WEIGHT.toString())
                .maxWeight(100.0)
                .state(DroneState.IDLE.name())
                .batteryPercentage(10)
                .build();
    }

    public static class DroneEntityTypeReference extends TypeReference<BasicResponse<DroneEntity>> {
    }
    // Oh ... s*t ,here we go again, another class with a nested class that extends TypeReference
}
