package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicOutLiteDto;
import org.example.dto.EpicPatchDto;
import org.example.service.EpicService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/epics")
@Tag(name = "Работа с группой задач")
@Slf4j
@Validated
public class EpicController {
    private final EpicService service;

    @PostMapping
    @Operation(description = "Создание группы")
    EpicOutDto create(@RequestBody @Valid EpicInDto epicInDto){
        EpicOutDto epicOutDto = service.create(epicInDto);
        log.info("Группа с id= {} создана", epicOutDto.getId());
        return epicOutDto;
    }

    @PatchMapping("/{epicId}")
    @Operation(description = "Редактирование группы")
    EpicOutLiteDto patchEpic(@PathVariable @Positive long epicId,
                         @RequestBody @Valid EpicPatchDto epicPatchDto){
        EpicOutLiteDto epicOutDto = service.patch(epicId, epicPatchDto);
        log.info("Реквизиты группы с id= {} изменены", epicOutDto.getId());
        return epicOutDto;
    }

    @PatchMapping("/{epicId}/addTask")
    @Operation(description = "Добавление задач в эпик")
    EpicOutLiteDto addTasks(@RequestHeader("X-Task-User-Id") long userId,
            @PathVariable @Positive long epicId,
                       @RequestParam long task){
        EpicOutLiteDto epicOutDto = service.addTask(userId, epicId, task);
        log.info("в группу с id= {} добавлены задачи", epicOutDto.getId());
        return epicOutDto;
    }

    @PatchMapping("/{epicId}/deleteTask")
    @Operation(description = "Удаление задач из эпика")
    EpicOutLiteDto deleteTasks(@RequestHeader("X-Task-User-Id") long userId,
            @PathVariable @Positive long epicId,
                       @RequestParam long task){
        EpicOutLiteDto epicOutDto = service.deleteTask(userId, epicId, task);
        log.info("в группу с id= {} удалены задачи", epicOutDto.getId());
        return epicOutDto;
    }

    @GetMapping("/{epicId}")
    @Operation(description = "Получение группы по id")
    EpicOutDto findEpicById(@PathVariable @Positive long epicId){
        EpicOutDto epicOutDto = service.findById(epicId);
        log.info("Найдена группа с id= {}", epicOutDto.getId());
        return epicOutDto;
    }

}
