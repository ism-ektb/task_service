package org.example.service;

import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicOutLiteDto;
import org.example.dto.EpicPatchDto;

import java.util.List;

public interface EpicService {
    /**
     * Создание нового эпика (группы)
     */
    EpicOutDto create(EpicInDto epicInDto);
    /**
     * Изменение эпика(группы)
     */
    EpicOutLiteDto patch(Long epicId, EpicPatchDto epicPatchDto);
    /**
     * Добавить задачу в группу
     */
    EpicOutLiteDto addTask(Long userId, Long epicId, Long taskId);
    /**
     * Удалить задачу из группы
     */
    EpicOutLiteDto deleteTask(Long userId, Long epicId, Long taskId);
    /**
     * Найти группу по id
     */
    EpicOutDto findById(Long epicId);
}
