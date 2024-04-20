package com.elmenus.droneia.application.advice;

import com.elmenus.droneia.application.drone.controller.DroneController;
import com.elmenus.droneia.application.medication.controller.MedicationController;
import com.elmenus.droneia.application.order.controller.OrderController;
import com.elmenus.droneia.domain.common.exception.ResourceNotFoundException;
import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.exception.DroneBusyException;
import com.elmenus.droneia.domain.drone.exception.LowBatteryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// I will add them all here for time saving
@RestControllerAdvice(assignableTypes = {
        OrderController.class,
        DroneController.class,
        MedicationController.class
})
public class DroneiaRestErrorHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BasicResponse<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new BasicResponse<>("Resource Not Found", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new BasicResponse<>("Bad Request", e.getMessage());
    }

    @ExceptionHandler(DroneBusyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BasicResponse<String> handleDroneBusyException(DroneBusyException e) {
        return new BasicResponse<>("Cannot Use This Drone", e.getMessage());
    }

    @ExceptionHandler(LowBatteryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BasicResponse<String> handleLowBatteryException(LowBatteryException e) {
        return new BasicResponse<>("Cannot Use This Drone", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BasicResponse<String> handleException(Exception e) {
        return new BasicResponse<>("Our app is not good enough sorry ... ", e.getMessage());
    }
}
