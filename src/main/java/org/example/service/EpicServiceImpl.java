package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicOutLiteDto;
import org.example.dto.EpicPatchDto;
import org.example.exeptions.ConflictException;
import org.example.exeptions.StorageException;
import org.example.mapper.EpicMapper;
import org.example.mapper.EpicPatcher;
import org.example.model.Epic;
import org.example.model.Task;
import org.example.repository.EpicRepository;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EpicServiceImpl implements EpicService{
    private final EpicRepository repository;
    private final EpicMapper mapper;
    private final EpicPatcher patcher;
    private final TaskRepository taskRepository;


    /**
     * Создание нового эпика (группы)
     *
     * @param epicInDto
     */
    @Override
    public EpicOutDto create(EpicInDto epicInDto) {
        Epic epic = repository.save(mapper.dtoToModel(epicInDto));
        return mapper.modelToDto(epic);
    }

    /**
     * Изменение эпика(группы)
     *
     * @param epicId
     * @param epicPatchDto
     */
    @Override
    public EpicOutLiteDto patch(Long epicId, EpicPatchDto epicPatchDto) {
        Epic epic = findEpicById(epicId);
        Epic newEpic = patcher.patch(epic, epicPatchDto);
        return mapper.modelToLiteDto(repository.save(newEpic));
    }

    /**
     * Добавить задачу в группу
     *
     * @param epicId
     * @param taskId
     */
    @Override
    public EpicOutLiteDto addTask(Long userId, Long epicId, Long taskId) {
        Task task = findTaskById(taskId);
        Epic epic = findEpicById(epicId);
        if (epic.getResponsibleId() != userId) throw new ConflictException(
                String.format("Пользователь с Id= %s не может добавлять задачи в группу Id= %s", userId, epicId));
        if (epic.getEventId() != task.getEventId()) throw new ConflictException(
                String.format("Невозможно добавить задачу с ID= %s в группу с ID = %s", taskId, epicId));
        task.setEpic(epic);
        taskRepository.save(task);
        return mapper.modelToLiteDto(findEpicById(epicId));
    }

    /**
     * Удалить задачу из группы
     *
     * @param epicId
     * @param taskId
     */
    @Override
    public EpicOutLiteDto deleteTask(Long userId, Long epicId, Long taskId) {
        Task task = findTaskById(taskId);
        Epic epic = findEpicById(epicId);
        if (epic.getResponsibleId() != userId) throw new ConflictException(
                String.format("Пользователь с Id= %s не может удалять задачи в группу Id= %s", userId, epicId));
        if (!(epic.getTasks().contains(task))) {
            System.out.println(epic.getTasks());
            throw new ConflictException(
                    String.format("задача с ID= %s отсутствует в группе с ID = %s", taskId, epicId));
        }
        task.setEpic(null);
        taskRepository.save(task);
        return mapper.modelToLiteDto(findEpicById(epicId));
    }

    /**
     * Найти группу по id
     *
     * @param epicId
     */
    @Override
    public EpicOutDto findById(Long epicId) {
        return mapper.modelToDto(findEpicById(epicId));
    }

    private Task findTaskById(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new StorageException(
                String.format("Задача с Id= %s не найдена", taskId)));
        return task;
    }

    private Epic findEpicById(Long epicId){
        Epic epic = repository.findById(epicId).orElseThrow(() -> new StorageException(
                String.format("Группа с ID= %s не найдена", epicId)));
        return epic;
    }
}
