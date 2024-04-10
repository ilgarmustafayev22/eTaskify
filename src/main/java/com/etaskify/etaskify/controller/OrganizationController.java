package com.etaskify.etaskify.controller;

import com.etaskify.etaskify.model.dto.OrganizationDto;
import com.etaskify.etaskify.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
