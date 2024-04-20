package com.elmenus.droneia.application.drone.controlle;

import com.elmenus.droneia.application.drone.controller.DroneController;
import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.model.*;
import com.elmenus.droneia.domain.drone.service.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WebFluxTest(DroneController.class)
public class DroneControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DroneService droneService;

    @Test
    @WithMockUser
    void test_CreateDrone_Success() {

        // Arrange
        var droneRegistrationRequest = DroneRegistrationRequest
                .builder()
                .serialNumber("1234")
                .model(DroneModel.valueOf(DroneModel.LIGHT_WEIGHT.toString()))
                .maxWeight(200.0)
                .build();

        var droneEntity = DroneEntity
                .builder()
                .id(UUID.randomUUID())
                .batteryPercentage(100)
                .serialNumber("1234")
                .model(DroneModel.LIGHT_WEIGHT)
                .maxWeight(200.0)
                .state(DroneState.IDLE)
                .build();


        var basicResponse = new BasicResponse<>(DroneService.DRONE_REGISTERED_SUCCESSFULLY, droneEntity);

        doAnswer(invocation -> Mono.just(basicResponse))
                .when(droneService)
                .registerDrone(droneRegistrationRequest);

        // Act
        var res = webTestClient
                .mutateWith(csrf())
                .post()
                .uri(DroneController.DRONE_PATH + DroneController.DRONE_PATH_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(droneRegistrationRequest), DroneRegistrationRequest.class)
                .exchange()
                .returnResult(BasicResponse.class);


        // Assert
        StepVerifier
                .create(res.getResponseBody())
                .consumeNextWith(response -> {
                    assertEquals(response.getMessage(), DroneService.DRONE_REGISTERED_SUCCESSFULLY);
                    assertEquals(response.getData().toString(), droneEntity.toString());
                }).verifyComplete();

    }

    @Test
    @WithMockUser
    void test_UpdateDrone_Success() {

        // Arrange
        var droneUpdateRequest = DroneUpdateRequest
                .builder()
                .id(UUID.randomUUID().toString())
                .serialNumber("ABC")
                .model(DroneModel.HEAVY_WEIGHT.toString())
                .maxWeight(200.0)
                .build();

        var droneEntity = DroneEntity
                .builder()
                .id(UUID.randomUUID())
                .batteryPercentage(100)
                .serialNumber("1234")
                .model(DroneModel.LIGHT_WEIGHT)
                .maxWeight(200.0)
                .state(DroneState.IDLE)
                .build();

        var basicResponse = new BasicResponse<>(DroneService.DRONE_UPDATED_SUCCESSFULLY, droneEntity);

        doAnswer(invocation -> Mono.just(basicResponse))
                .when(droneService)
                .updateDrone(droneUpdateRequest);

        // Act
        var res = webTestClient
                .mutateWith(csrf())
                .put()
                .uri(DroneController.DRONE_PATH + DroneController.DRONE_PATH_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(droneUpdateRequest), DroneUpdateRequest.class)
                .exchange()
                .returnResult(BasicResponse.class);

        // Assert
        StepVerifier
                .create(res.getResponseBody())
                .consumeNextWith(response -> {
                    assertEquals(response.getMessage(), DroneService.DRONE_UPDATED_SUCCESSFULLY);
                    assertEquals(response.getData().toString(), droneEntity.toString());
                }).verifyComplete();
    }

    // same approach for other methods

}
