package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public TaskDto createTask(@Validated @RequestBody NewTaskDto task) {

        return service.createTask(task);
    }

    @PatchMapping(path = "/{id}")
    public TaskDto updateTask(@RequestHeader("X-Task-User-Id") Long userId, @PathVariable Long id,
                              @Validated @RequestBody UpdateTaskDto task) {
        return service.updateTask(userId, id, task);
    }

    @GetMapping(path = "/{id}")
    public TaskDto getTask(@PathVariable long id) {
        return service.getTask(id);
    }

    @GetMapping
    public List<TaskDto> getTasks(@RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                  @RequestParam(required = false, defaultValue = "10", name = "size") int size,
                                  @RequestParam(required = false) Long eventId,
                                  @RequestParam(required = false) Long assignTo,
                                  @RequestParam(required = false) Long authorId) {

        return service.getTasks(page, size, eventId, assignTo, authorId);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@RequestHeader("X-Task-User-Id") Long userId, @PathVariable long id) {
        service.delete(userId, id);
    }
}



