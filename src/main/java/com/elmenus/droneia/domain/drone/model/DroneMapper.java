package com.elmenus.droneia.domain.drone.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DroneMapper {
    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);


    DroneEntity toEntity(DroneRegistrationRequest request);
}
