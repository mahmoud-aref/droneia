package com.elmenus.droneia.infrastructure.cron;

import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.infrastructure.datasource.sql.drone.DroneRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@AllArgsConstructor
public class DroneBatterySimulator {

    private final DroneRepository droneRepository;

    @Async("mainAsyncExecutor")
    @Scheduled(fixedDelayString = "${drone.battery-drain-rate}")
    public void simulateBatteryDrain() {
        droneRepository
                .findAll()
                .map(DroneEntity::drainBattery)
                .flatMap(droneRepository::save)
                .map(DroneEntity::batteryDrainLogMessage)
                .log()
                .subscribe();
    }

}
