package com.etaskify.etaskify.controller;

import com.etaskify.etaskify.exception.TaskNotFoundException;
import com.etaskify.etaskify.model.dto.TaskDto;
import com.etaskify.etaskify.model.enums.TaskStatus;
import com.etaskify.etaskify.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;


    @Test
    void createTaskAndAssignToUsers_success_returnsCreated() {
        TaskDto taskDto = new TaskDto(/* ... task data */);
        List<Long> userIds = Arrays.asList(1L, 2L);
        doNothing().when(taskService).createTaskAndAssignToUsers(taskDto, userIds);

        ResponseEntity<Void> result = taskController.createTaskAndAssignToUsers(taskDto, userIds);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(taskService).createTaskAndAssignToUsers(taskDto, userIds);
    }

    @Test
    void findTasksByUserId_tasksFound_returnsOkAndTaskList() {
        long userId = 1L;
        List<TaskDto> expectedTasks = Arrays.asList(
                new TaskDto(/* ... task1 data */),
                new TaskDto(/* ... task2 data */)
        );
        when(taskService.findTasksByUserId(userId)).thenReturn(expectedTasks);

        ResponseEntity<List<TaskDto>> result = taskController.findTasksByUserId(userId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedTasks);
    }

    @Test
    void findTasksByOrganizationId_tasksFound_returnsOkAndTaskList() {
        long organizationId = 1L;
        List<TaskDto> expectedTasks = Arrays.asList(
                new TaskDto(/* ... task1 data */),
                new TaskDto(/* ... task2 data */)
        );
        when(taskService.findTasksByOrganizationId(organizationId)).thenReturn(expectedTasks);

        ResponseEntity<List<TaskDto>> result = taskController.findTasksByOrganizationId(organizationId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedTasks);
    }

    @Test
    void findById_taskFound_returnsOkAndTaskDto() {
        long taskId = 1L;
        TaskDto expectedTask = new TaskDto(/* ... task data */);
        when(taskService.findById(taskId)).thenReturn(expectedTask);

        ResponseEntity<TaskDto> result = taskController.findById(taskId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedTask);
    }

    @Test
    void findById_taskNotFound_returnsNotFound() {
        long nonExistentTaskId = 999L;
    }

    @Test
    void findAllTasks_tasksExist_returnsOkAndTaskList() {
        List<TaskDto> expectedTasks = Arrays.asList(
                new TaskDto(/* ... task1 data */),
                new TaskDto(/* ... task2 data */)
        );
        when(taskService.findAllTasks()).thenReturn(expectedTasks);

        ResponseEntity<List<TaskDto>> result = taskController.findAllTasks();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedTasks);
    }

    @Test
    void findAllTasks_noTasksExist_returnsOkAndEmptyList() {
        List<TaskDto> emptyList = Collections.emptyList();
        when(taskService.findAllTasks()).thenReturn(emptyList);

        ResponseEntity<List<TaskDto>> result = taskController.findAllTasks();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEmpty();
    }

    @Test
    void updateTaskStatus_success_returnsOk() {
        long taskId = 1L;
        TaskStatus newStatus = TaskStatus.IN_PROGRESS;
        doNothing().when(taskService).updateTaskStatus(taskId, newStatus);

        ResponseEntity<Void> result = taskController.updateTaskStatus(taskId, newStatus);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(taskService).updateTaskStatus(taskId, newStatus);
    }


    @Test
    void updateTaskStatus_taskNotFound_returnsNotFound() {
        long nonExistentTaskId = 999L;
        TaskStatus status = TaskStatus.IN_PROGRESS;
    }

    @Test
    void deleteTask_success_returnsNoContent() {
        long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        ResponseEntity<Void> result = taskController.deleteTask(taskId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(taskService).deleteTask(taskId);
    }

    @Test
    void deleteTask_taskNotFound_returnsNotFound() {
        long nonExistentTaskId = 999L;
    }

}
