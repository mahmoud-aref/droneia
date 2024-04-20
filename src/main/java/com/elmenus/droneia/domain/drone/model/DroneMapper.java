package com.elmenus.droneia.domain.drone.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DroneMapper {
    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "batteryPercentage", ignore = true)
    @Mapping(target = "state", ignore = true)
    DroneEntity toEntity(DroneRegistrationRequest request);


    DroneEntity toEntity(DroneUpdateRequest request);

    @Mapping(target = "id", source = "existingDrone.id")
    @Mapping(target = "serialNumber", source = "droneUpdateRequest.serialNumber")
    @Mapping(target = "model", source = "droneUpdateRequest.model")
    @Mapping(target = "maxWeight", source = "droneUpdateRequest.maxWeight")
    @Mapping(target = "batteryPercentage", source = "droneUpdateRequest.batteryPercentage")
    @Mapping(target = "state", source = "droneUpdateRequest.state")
    DroneEntity toEntity(DroneUpdateRequest droneUpdateRequest, DroneEntity existingDrone);


}
