package com.elmenus.droneia.domain.drone.service;

import com.elmenus.droneia.domain.drone.model.*;
import com.elmenus.droneia.domain.drone.service.impl.DroneServiceImpl;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;



public class DroneServiceTest {

    @InjectMocks
    private DroneServiceImpl droneService;

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneMapper droneMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
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
                .batteryPercentage(100)
                .serialNumber("1234")
                .model(DroneModel.LIGHT_WEIGHT)
                .maxWeight(200.0)
                .state(DroneState.IDLE)
                .build();


        when(droneMapper.toEntity(droneRegistrationRequest)).thenReturn(droneEntity);

        droneEntity.setId(UUID.randomUUID());
        when(droneRepository.save(droneEntity)).thenReturn(Mono.just(droneEntity));


        // Act
        var result = droneService.registerDrone(droneRegistrationRequest);

        // Assert
        StepVerifier
                .create(result)
                .consumeNextWith(response -> {
                    assertEquals(response.getMessage(), DroneService.DRONE_REGISTERED_SUCCESSFULLY);
                    assertEquals(response.getData(), droneEntity);
                }).verifyComplete();
    }

    @Test
    void test_droneRequest_BadData() {

        // Arrange
        var unAcceptedSerial = "x".repeat(101);
        var droneRegistrationRequest = DroneRegistrationRequest
                .builder()
                .serialNumber(unAcceptedSerial)
                .model(DroneModel.valueOf(DroneModel.LIGHT_WEIGHT.toString()))
                .maxWeight(200.0)
                .build();


        // Create Validator instance
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Perform validation
        Set<ConstraintViolation<DroneRegistrationRequest>> violations = validator.validate(droneRegistrationRequest);

        // Assert
        assertFalse(violations.isEmpty());
    }

    /*
     NoMuch Business Logic to test , so I will cover crud in controller test
     */
}
