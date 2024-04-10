package com.etaskify.etaskify.service;

import com.etaskify.etaskify.model.dto.OrganizationDto;

public interface OrganizationService {

    OrganizationDto findById(long id);

}
