package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.repositroy.OrganizationRepository;
import com.etaskify.etaskify.exception.OrganizationNotFoundException;
import com.etaskify.etaskify.mapper.OrganizationMapper;
import com.etaskify.etaskify.model.dto.OrganizationDto;
import com.etaskify.etaskify.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationMapper mapper;
    private final OrganizationRepository repository;

    @Override
    public OrganizationDto findById(long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with id " + id));
    }

}
