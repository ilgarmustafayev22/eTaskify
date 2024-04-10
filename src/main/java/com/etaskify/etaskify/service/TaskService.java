package com.etaskify.etaskify.service;

import com.etaskify.etaskify.model.dto.TaskDto;
import com.etaskify.etaskify.model.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    void createTaskAndAssignToUsers(TaskDto task, List<Long> userId);

    List<TaskDto> findTasksByUserId(long id);

    List<TaskDto> findTasksByOrganizationId(long id);

    TaskDto findById(long id);

    List<TaskDto> findAllTasks();

    void updateTaskStatus(long id, TaskStatus taskStatus);

    void deleteTask(long id);

}
