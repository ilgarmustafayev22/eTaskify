package com.etaskify.etaskify.mapper;

import com.etaskify.etaskify.dao.entity.TaskEntity;
import com.etaskify.etaskify.model.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(source = "organization.id", target = "organizationId")
    TaskDto toDto(TaskEntity taskEntity);

}
