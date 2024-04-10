package com.etaskify.etaskify.controller;

import com.etaskify.etaskify.model.dto.OrganizationDto;
import com.etaskify.etaskify.service.OrganizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationControllerTest {

    @Mock
    private OrganizationService organizationService;

    @InjectMocks
    private OrganizationController organizationController;

    @Test
    void findById_validId_returnsOrganizationDto() {
        long organizationId = 1L;
        OrganizationDto expectedDto = new OrganizationDto("MyCompany", "Test Organization", " Baku");
        when(organizationService.findById(organizationId)).thenReturn(expectedDto);

        ResponseEntity<OrganizationDto> result = organizationController.findById(organizationId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedDto);
    }

}
