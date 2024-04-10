package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
import com.etaskify.etaskify.dao.repositroy.OrganizationRepository;
import com.etaskify.etaskify.exception.OrganizationNotFoundException;
import com.etaskify.etaskify.mapper.OrganizationMapper;
import com.etaskify.etaskify.model.dto.OrganizationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceImplTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @Test
    void testFindById_Success() {
        long organizationId = 1L;
        OrganizationEntity organization = new OrganizationEntity(/* ..*/); // Sample entity
        OrganizationDto expectedDto = new OrganizationDto(/* ..*/); // Sample DTO

        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        when(organizationMapper.toDto(organization)).thenReturn(expectedDto);

        OrganizationDto result = organizationService.findById(organizationId);

        assertEquals(expectedDto, result);
    }

    @Test
    void testFindById_NotFound() {
        long organizationId = 1L;
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());

        assertThrows(OrganizationNotFoundException.class, () -> organizationService.findById(organizationId));
    }

}

