package org.example.service;

import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
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
    EpicOutDto patch(Long epicId, EpicPatchDto epicPatchDto);
    /**
     * Добавить задачу в группу
     */
    EpicOutDto addTask(Long epicId, Long taskId);
    /**
     * Удалить задачу из группы
     */
    EpicOutDto deleteTask(Long epicId, Long taskId);
    /**
     * Найти группу по id
     */
    EpicOutDto findById(Long epicId);
}
