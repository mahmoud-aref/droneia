package com.elmenus.droneia.domain.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Drone Battery Low Less Than 25%")
public class LowBatteryException extends RuntimeException {
    public LowBatteryException(String message) {
        super(message);
    }
}
