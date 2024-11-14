package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.model.Task;
import org.example.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping(path = "/tasks")
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class TaskController {

    private TaskService taskService;

    @PostMapping
    public Task createTask(@Validated @RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PatchMapping(path = "/{id}")
    public Task updateTask(@PathVariable Long id, @Validated @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @GetMapping(path = "/{id}")
    public Task getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.delete(id);
    }
}


/*
POST /tasks - создание задачи

PATCH /tasks/{id} - обновление задачи по id (проверка по header - обновлять могут только автор или исполнитель задачи, нельзя обновить createdDateTime и authorId)

GET /tasks/{id} - получение задачи по id

GET /tasks?page={page}&size={size}&eventId={eventId}&assignTo={assigneeId}&authorId={authorId} - получение задач с пагинацией и необязательными фильтрами по id события, id исполнителя и id автора

DELETE /tasks/{id} - удаление задачи по id (проверка по header - удалить может только автор)*/
