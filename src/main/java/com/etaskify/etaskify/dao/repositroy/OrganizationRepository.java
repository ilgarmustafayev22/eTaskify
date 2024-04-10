package com.etaskify.etaskify.dao.repositroy;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

}
