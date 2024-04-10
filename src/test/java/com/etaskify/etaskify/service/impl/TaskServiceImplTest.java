package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.TaskEntity;
import com.etaskify.etaskify.dao.entity.UserEntity;
import com.etaskify.etaskify.dao.repositroy.TaskRepository;
import com.etaskify.etaskify.dao.repositroy.UserRepository;
import com.etaskify.etaskify.exception.TaskNotFoundException;
import com.etaskify.etaskify.mapper.TaskMapper;
import com.etaskify.etaskify.model.dto.MailDto;
import com.etaskify.etaskify.model.dto.TaskDto;
import com.etaskify.etaskify.model.enums.TaskStatus;
import com.etaskify.etaskify.model.enums.UserRole;
import com.etaskify.etaskify.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private MailService mailService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTaskAndAssignToUsers_success() {
        // Test data setup
        TaskDto taskDto = new TaskDto(1L, "title", "description", LocalDateTime.now(), TaskStatus.ASSIGNED);
        List<Long> userIds = List.of(1L, 2L);
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        user1.setTasks(new HashSet<>());
        user2.setTasks(new HashSet<>());
        TaskEntity taskEntity = new TaskEntity();

        //verify(mailService).sendMailAboutAssignedTask(anyList(), any(TaskEntity.class));
    }

    @Test
    void testFindTasksByUserId() {
        // Test Data Setup
        long userId = 1L;
        TaskEntity taskEntity1 = new TaskEntity();
                TaskEntity taskEntity2 = new TaskEntity();
                TaskDto taskDto1 = new TaskDto();
                TaskDto taskDto2 = new TaskDto();

                // Mocks Configuration
                when(taskRepository.findByUsers_Id(userId)).thenReturn(List.of(taskEntity1, taskEntity2));
        when(taskMapper.toDto(taskEntity1)).thenReturn(taskDto1);
        when(taskMapper.toDto(taskEntity2)).thenReturn(taskDto2);

        // Execution
        List<TaskDto> results = taskService.findTasksByUserId(userId);

        // Verification
        assertThat(results).hasSize(2);
        assertThat(results).containsExactlyInAnyOrder(taskDto1, taskDto2);

        verify(taskRepository).findByUsers_Id(userId);
        verify(taskMapper).toDto(taskEntity1);
        verify(taskMapper).toDto(taskEntity2);
    }

    @Test
    void testFindTasksByOrganizationId() {
        // Test Data Setup
        long organizationId = 1L;
        TaskEntity taskEntity1 = new TaskEntity();
        TaskEntity taskEntity2 = new TaskEntity();
        TaskDto taskDto1 = new TaskDto();
        TaskDto taskDto2 = new TaskDto();

                // Configure mocks
                when(taskRepository.findByOrganization_Id(organizationId)).thenReturn(List.of(taskEntity1, taskEntity2));
        when(taskMapper.toDto(taskEntity1)).thenReturn(taskDto1);
        when(taskMapper.toDto(taskEntity2)).thenReturn(taskDto2);

        // Execution
        List<TaskDto> results = taskService.findTasksByOrganizationId(organizationId);

        // Verification
        assertThat(results).hasSize(2);
        assertThat(results).containsExactlyInAnyOrder(taskDto1, taskDto2);

        verify(taskRepository).findByOrganization_Id(organizationId);
        verify(taskMapper).toDto(taskEntity1);
        verify(taskMapper).toDto(taskEntity2);
    }

    @Test
    void testFindById_taskFound() {
        // Test Data Setup
        long taskId = 1L;
        TaskEntity taskEntity = new TaskEntity();
        TaskDto taskDto = new TaskDto();

                // Configure mocks
                when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toDto(taskEntity)).thenReturn(taskDto);

        // Execution
        TaskDto result = taskService.findById(taskId);

        // Verification
        assertThat(result).isEqualTo(taskDto);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).toDto(taskEntity);
    }

    @Test
    void testFindById_taskNotFound() {
        // Test Data Setup
        long taskId = 1L;

        // Configure mocks
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Execution and Verification
        assertThrows(TaskNotFoundException.class, () -> taskService.findById(taskId));
        verify(taskRepository).findById(taskId);
        verifyNoInteractions(taskMapper); // Mapper should not be called
    }

    @Test
    void testFindAllTasks() {
        // Test Data Setup
        TaskEntity taskEntity1 = new TaskEntity();
        TaskEntity taskEntity2 = new TaskEntity();
        TaskDto taskDto1 = new TaskDto();
        TaskDto taskDto2 = new TaskDto();
                // Configure mocks
                when(taskRepository.findAll()).thenReturn(List.of(taskEntity1, taskEntity2));
        when(taskMapper.toDto(taskEntity1)).thenReturn(taskDto1);
        when(taskMapper.toDto(taskEntity2)).thenReturn(taskDto2);

        // Execution
        List<TaskDto> results = taskService.findAllTasks();

        // Verification
        assertThat(results).hasSize(2);
        assertThat(results).containsExactlyInAnyOrder(taskDto1, taskDto2);

        verify(taskRepository).findAll();
        verify(taskMapper).toDto(taskEntity1);
        verify(taskMapper).toDto(taskEntity2);
    }

    @Test
    void testUpdateTaskStatus_taskFound() {
        // Test Data Setup
        long taskId = 1L;
        TaskStatus newStatus = TaskStatus.DONE;
        TaskEntity taskEntity = new TaskEntity();

                // Configure mocks
                when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        // Execution
        taskService.updateTaskStatus(taskId, newStatus);

        // Verification
        verify(taskRepository).findById(taskId);
        //verify(any(TaskEntity.class)).setStatus(newStatus);
       // verify(taskRepository).save(taskEntity); // Assuming save is used for updates
    }

    @Test
    void testUpdateTaskStatus_taskNotFound() {
        // Test Data Setup
        long taskId = 1L;
        TaskStatus newStatus = TaskStatus.DONE;

        // Configure mocks
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Execution and Verification
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskStatus(taskId, newStatus));
        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(taskRepository); // Ensure no save is attempted
    }

    @Test
    void testDeleteTask() {
        long taskId = 1L;

        // Execution and Verification
        assertDoesNotThrow(() -> taskService.deleteTask(taskId));
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void testSendMailAboutAssignedTask() {
        // Test Data Setup
        UserEntity user1 = new UserEntity();
                UserEntity user2 = new UserEntity();
                TaskEntity taskEntity = new TaskEntity();

                // Configure mocks (if needed)
                // ...

                // Call the method
                taskService.sendMailAboutAssignedTask(List.of(user1, user2), taskEntity);

        // Capture MailDtos sent to mailService
        ArgumentCaptor<MailDto> mailDtoCaptor = ArgumentCaptor.forClass(MailDto.class);
        verify(mailService, times(2)).sendMail(mailDtoCaptor.capture());

        // Assertions on captured MailDto objects
        List<MailDto> capturedMails = mailDtoCaptor.getAllValues();
        assertThat(capturedMails).hasSize(2);

        // Check the first email
        assertThat(capturedMails.get(0).getTo()).isEqualTo(user1.getEmail());
        assertThat(capturedMails.get(0).getSubject()).isEqualTo("This assignment is for you");
        // ... (add more checks for text)

        // Similarly, check the second email
        // ...
    }

}
