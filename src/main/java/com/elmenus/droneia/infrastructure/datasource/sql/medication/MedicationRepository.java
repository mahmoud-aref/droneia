package com.elmenus.droneia.infrastructure.datasource.sql.medication;

import com.elmenus.droneia.domain.medication.model.MedicationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicationRepository extends ReactiveCrudRepository<MedicationEntity, UUID> {
}
