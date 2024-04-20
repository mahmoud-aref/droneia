package com.elmenus.droneia.factory;

import com.elmenus.droneia.domain.drone.model.DroneModel;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;

public class FeaturesMockingFactory {

    public static DroneRegistrationRequest getMockDroneRegistrationRequest() {
        return DroneRegistrationRequest.builder()
                .serialNumber("1234567890")
                .model(DroneModel.LIGHT_WEIGHT)
                .maxWeight(100)
                .build();
    }

}
