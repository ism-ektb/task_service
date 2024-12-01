package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
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
@Tag(name = "Контроллер задач", description = "Контроллер для создания, обновления, получения и удаления задач")
public class TaskController {

    private final TaskService service;

    @PostMapping
    @Operation(summary = "Создание задачи", description = "Позволяет создать задачу")
    @ApiResponse(responseCode = "400", description = "Неверный запрос — проверьте корректность параметров")
    @ApiResponse(responseCode = "404", description = "Не найдено")
    @ApiResponse(responseCode = "409", description = "Конфликт")
    public TaskDto createTask(@Validated @RequestBody @Parameter(description = "Dto новой задачи", required = true) NewTaskDto task) {

        return service.createTask(task);
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Обновление задачи по id",
            description = "Обновлять могут только автор или исполнитель задачи, нельзя обновить createdDateTime и authorId")
    @ApiResponse(responseCode = "400", description = "Неверный запрос — проверьте корректность параметров")
    @ApiResponse(responseCode = "404", description = "Неверный запрос — в базе нет задачи с таким идентификатором")
    @ApiResponse(responseCode = "409", description = "Неверный запрос — у пользователя нет прав на обновление задачи")
    public TaskDto updateTask(@RequestHeader("X-Task-User-Id") @Parameter(description = "Идентификатор пользователя, обновляющего задачу", example = "1") Long userId,
                              @PathVariable @Parameter(description = "Идентификатор задачи", example = "2") Long id,
                              @Validated @RequestBody @Parameter(description = "Dto обновляемой задачи", required = true) UpdateTaskDto task) {
        return service.updateTask(userId, id, task);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Получение задачи", description = "Позволяет получить задачу по id")
    @ApiResponse(responseCode = "400", description = "Неверный запрос — проверьте корректность параметров")
    @ApiResponse(responseCode = "404", description = "Неверный запрос — в базе нет задачи с таким идентификатором")
    @ApiResponse(responseCode = "409", description = "Конфликт")
    public TaskDto getTask(@PathVariable @Parameter(description = "Идентификатор задачи", example = "1") long id) {
        return service.getTask(id);
    }

    @GetMapping
    @Operation(summary = "Получение задач",
            description = "Позволяет получить задачи с пагинацией и необязательными фильтрами по id события, id исполнителя и id автора")
    @ApiResponse(responseCode = "400", description = "Неверный запрос — проверьте корректность параметров")
    @ApiResponse(responseCode = "404", description = "По указанному запросу данных в базе нет")
    @ApiResponse(responseCode = "409", description = "Конфликт")
    public List<TaskDto> getTasks(@RequestParam(defaultValue = "0", name = "page") @Parameter(description = "Страница", example = "5") int page,
                                  @RequestParam(defaultValue = "10", name = "size") @Parameter(description = "Размер страницы", example = "10") int size,
                                  @RequestParam @Nullable @Parameter(description = "Идентификатор события", example = "3") Long eventId,
                                  @RequestParam  @Nullable @Parameter(description = "Идентификатор исполнителя", example = "4") Long assignTo,
                                  @RequestParam  @Nullable @Parameter(description = "Идентификатор автора задачи", example = "7") Long authorId) {

        return service.getTasks(page, size, eventId, assignTo, authorId);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Удаление задачи", description = "Удалить задачу может только её автор")
    @ApiResponse(responseCode = "400", description = "Неверный запрос — проверьте корректность параметров")
    @ApiResponse(responseCode = "404", description = "Неверный запрос — в базе нет задачи с таким идентификатором")
    @ApiResponse(responseCode = "409", description = "Неверный запрос — у пользователя нет прав на удаление задачи")
    public void deleteTask(@RequestHeader("X-Task-User-Id") @Parameter(description = "Идентификатор пользователя, удаляющего задачу") Long userId,
                           @PathVariable @Parameter(description = "Идентификатор задачи", example = "2") long id) {
        service.delete(userId, id);
    }
}



