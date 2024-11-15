package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.model.Task;
import org.example.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping(path = "/tasks")
@AllArgsConstructor
@Validated
public class TaskController {

    private TaskService taskService;

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
    public List<TaskDto> getTasks(@RequestParam int page, @RequestParam int size,
                                  @RequestParam Long eventId, @RequestParam Long assigneeId, @RequestParam Long authorId) {

        return taskService.getTasks(page,size,eventId, assigneeId, authorId);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@RequestHeader("X-Task-User-Id") Long userId, @PathVariable long id) {
        taskService.delete(userId, id);
    }
}



