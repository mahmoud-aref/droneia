package com.elmenus.droneia.domain.order.validation;

import com.elmenus.droneia.domain.order.model.OrderLoadingRequest;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import com.elmenus.droneia.infrastructure.datasource.sql.medication.MedicationRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
@AllArgsConstructor
public class DroneMedicationValidator implements ConstraintValidator<ValidLoad, OrderLoadingRequest> {

    private final MedicationRepository medicationRepository;
    private final DroneRepository droneRepository;

    @Override
    public void initialize(ValidLoad constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(OrderLoadingRequest orderLoadingRequest, ConstraintValidatorContext constraintValidatorContext) {

        AtomicReference<Double> totalMedLoad = new AtomicReference<>(0.0);
        return orderLoadingRequest
                .getMedicationItems()
                .stream()
                .anyMatch(medicationLoad -> {
                    medicationRepository
                            .findById(UUID.fromString(medicationLoad.getMedicationId()))
                            .flatMap(medicationEntity ->
                                    Mono.just(medicationEntity.getWeight() * medicationLoad.getMedicationQuantity())
                            ).doOnNext(totalMedsOfQuantityWeight -> {
                                totalMedLoad.updateAndGet(old -> old + totalMedsOfQuantityWeight);
                            }).block();

                    return droneRepository
                            .findById(UUID.fromString(orderLoadingRequest.getDroneId()))
                            .flatMap(droneEntity -> Mono.just(droneEntity.getMaxWeight() < totalMedLoad.get()))
                            .block();
                });
    }


}
