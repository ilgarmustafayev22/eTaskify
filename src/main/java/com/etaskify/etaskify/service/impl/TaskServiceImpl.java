package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
import com.etaskify.etaskify.dao.entity.TaskEntity;
import com.etaskify.etaskify.dao.entity.UserEntity;
import com.etaskify.etaskify.dao.repositroy.TaskRepository;
import com.etaskify.etaskify.dao.repositroy.UserRepository;
import com.etaskify.etaskify.exception.TaskNotFoundException;
import com.etaskify.etaskify.mapper.TaskMapper;
import com.etaskify.etaskify.model.dto.MailDto;
import com.etaskify.etaskify.model.dto.TaskDto;
import com.etaskify.etaskify.model.enums.TaskStatus;
import com.etaskify.etaskify.service.MailService;
import com.etaskify.etaskify.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public void createTaskAndAssignToUsers(TaskDto task, List<Long> userIds) {
        List<UserEntity> assignedUsers = userIds.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        TaskEntity taskEntity = taskRepository.save(TaskEntity.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .status(TaskStatus.ASSIGNED)
                .organization(OrganizationEntity.builder().id(task.getOrganizationId()).build())
                .build());

        assignedUsers.forEach(assignedUser -> assignedUser.addTask(taskEntity));
        sendMailAboutAssignedTask(assignedUsers, taskEntity);
    }

    @Override
    public List<TaskDto> findTasksByUserId(long id) {
        return taskRepository.findByUsers_Id(id).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findTasksByOrganizationId(long id) {
        return taskRepository.findByOrganization_Id(id).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto findById(long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id + " + id));
    }

    @Override
    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateTaskStatus(long id, TaskStatus taskStatus) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id + " + id));
        task.setStatus(taskStatus);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    protected void sendMailAboutAssignedTask(List<UserEntity> users, TaskEntity task) {
        users.stream().map(user -> MailDto.builder()
                .to(user.getEmail())
                .text(String.format("Task title: %s\n," +
                                " description: %s\n," +
                                " deadline: %s\n," +
                                " status: %s\n",
                        task.getTitle(), task.getDescription(), task.getDeadline(), task.getStatus()))
                .subject("This assignment is for you")
                .build()).forEach(mailService::sendMail);
    }

}
