package org.example.controller;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.service.TaskServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tasks")
@AllArgsConstructor
@Validated
public class TaskController {

    private TaskServiceImpl taskService;

    @PostMapping
    public TaskDto createTask(@Validated @RequestBody NewTaskDto task) {

        return taskService.createTask(task);
    }

    @PatchMapping(path = "/{id}")
    public TaskDto updateTask(@RequestHeader("X-Task-User-Id") Long userId, @PathVariable Long id, @Validated @RequestBody UpdateTaskDto task) {
        return taskService.updateTask(userId, id, task);
    }

    @GetMapping(path = "/{id}")
    public TaskDto getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }

    @GetMapping
    public List<TaskDto> getTasks(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size,
                                  @RequestParam @Nullable Long eventId, @RequestParam  @Nullable Long assignTo, @RequestParam  @Nullable Long authorId) {

        return taskService.getTasks(page,size,eventId, assignTo, authorId);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@RequestHeader("X-Task-User-Id") Long userId, @PathVariable long id) {
        taskService.delete(userId, id);
    }
}



