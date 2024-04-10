package com.etaskify.etaskify.controller;

import com.etaskify.etaskify.model.dto.TaskDto;
import com.etaskify.etaskify.model.enums.TaskStatus;
import com.etaskify.etaskify.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> createTaskAndAssignToUsers(@RequestBody TaskDto task,
                                                           @RequestParam List<Long> userIds) {
        taskService.createTaskAndAssignToUsers(task, userIds);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDto>> findTasksByUserId(@PathVariable long userId) {
        return ResponseEntity.ok(taskService.findTasksByUserId(userId));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<TaskDto>> findTasksByOrganizationId(@PathVariable long organizationId) {
        return ResponseEntity.ok(taskService.findTasksByOrganizationId(organizationId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<List<TaskDto>> findAllTasks() {
        return ResponseEntity.ok(taskService.findAllTasks());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable long id,
                                                 @RequestParam TaskStatus status) {
        taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
