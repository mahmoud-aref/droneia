package com.elmenus.droneia.domain.medication.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    MedicationMapper INSTANCE = Mappers.getMapper(MedicationMapper.class);

    @Mapping(target = "id", ignore = true)
    MedicationEntity toEntity(MedicationRegistrationRequest request);

    MedicationEntity toEntity(MedicationUpdateRequest request);

    @Mapping(target = "id", source = "existingMedication.id")
    @Mapping(target = "name", source = "medicationUpdateRequest.name")
    @Mapping(target = "weight", source = "medicationUpdateRequest.weight")
    @Mapping(target = "code", source = "medicationUpdateRequest.code")
    @Mapping(target = "imageUrl", source = "medicationUpdateRequest.imageUrl")
    MedicationEntity toEntity(MedicationUpdateRequest medicationUpdateRequest, MedicationEntity existingMedication);


}
