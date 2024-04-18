package com.elmenus.droneia.domain.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Drone is busy with another order")
public class DroneBusyException extends RuntimeException {
    public DroneBusyException(String message) {
        super(message);
    }
}
