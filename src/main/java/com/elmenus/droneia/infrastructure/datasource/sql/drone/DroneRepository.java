package com.elmenus.droneia.infrastructure.datasource.sql.drone;

import com.elmenus.droneia.domain.drone.model.DroneEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DroneRepository extends ReactiveCrudRepository<DroneEntity, UUID> {
}
