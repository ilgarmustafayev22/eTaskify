package com.etaskify.etaskify.mapper;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
import com.etaskify.etaskify.model.dto.OrganizationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {

    OrganizationDto toDto(OrganizationEntity organization);

}
