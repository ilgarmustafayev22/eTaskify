package com.etaskify.etaskify.dao.repositroy;

import com.etaskify.etaskify.dao.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByUsers_Id(long id);

    List<TaskEntity> findByOrganization_Id(long id);

}
