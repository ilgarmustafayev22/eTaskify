package com.etaskify.etaskify.mapper;

import com.etaskify.etaskify.dao.entity.UserEntity;
import com.etaskify.etaskify.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


   // @Mapping(source = "name", target = "name")
   // @Mapping(source = "surname", target = "surname")
   // @Mapping(source = "email", target = "email")
    @Mapping(source = "organization.id", target = "organizationId")
    UserDto toDto(UserEntity userEntity);

    @Mapping(source = "organizationId", target = "organization.id")
    UserEntity toEntity(UserDto userDto);

}