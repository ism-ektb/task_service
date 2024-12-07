package org.example.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicOutLiteDto;
import org.example.dto.EpicPatchDto;
import org.example.dto.external.EventDto;
import org.example.exeptions.ConflictException;
import org.example.exeptions.StorageException;
import org.example.feignclient.JSONPlaceHolderClientEvent;
import org.example.feignclient.JSONPlaceHolderClientUser;
import org.example.mapper.EpicMapper;
import org.example.mapper.EpicPatcher;
import org.example.model.Epic;
import org.example.model.Task;
import org.example.repository.EpicRepository;
import org.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class EpicServiceImpl implements EpicService{
    private final EpicRepository repository;
    private final EpicMapper mapper;
    private final EpicPatcher patcher;
    private final TaskRepository taskRepository;

    @Autowired
    private JSONPlaceHolderClientEvent clientEvent;

    @Autowired
    private JSONPlaceHolderClientUser clientUser;


    /**
     * Создание нового эпика (группы)
     *
     * @param epicInDto
     */
    @Override
    public EpicOutDto create(EpicInDto epicInDto) {
        try {
            var userDto = getClientUser().getUser(1L, "password", epicInDto.getResponsibleId());
        } catch (Exception e) {
            throw new StorageException("Пользователь с id = " + epicInDto.getResponsibleId() + " не найден");
        }
        var organizers = getClientEvent().getEventOrganizers(1L, epicInDto.getEventId());
        if (organizers.stream().noneMatch(x -> x.getUserId() == epicInDto.getResponsibleId()))
            throw new ConflictException("Пользователь с id = " + epicInDto.getResponsibleId()
                    + " не относится к команде организаторов");
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
        try {
            var userDto = getClientUser().getUser(1L, "password", epicPatchDto.getResponsibleId());
        } catch (Exception e) {
            throw new StorageException("Пользователь с id = " + epicPatchDto.getResponsibleId() + " не найден");
        }
        Epic epic = findEpicById(epicId);
        var organizers = getClientEvent().getEventOrganizers(1L, epic.getEventId());
        if (organizers.stream().noneMatch(x -> x.getUserId() == epicPatchDto.getResponsibleId()))
            throw new ConflictException("Пользователь с id = " + epicPatchDto.getResponsibleId()
                    + " не относится к команде организаторов");
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
        try {
            var userDto = getClientUser().getUser(1L, "password", userId);
        } catch (Exception e) {
            throw new StorageException("Пользователь с id = " + userId + " не найден");
        }
        Task task = findTaskById(taskId);
        Epic epic = findEpicById(epicId);
        var organizers = getClientEvent().getEventOrganizers(1L, task.getEventId());
        if (organizers.stream().noneMatch(x -> x.getUserId() == task.getAssigneeId()))
            throw new ConflictException("Пользователь с id = " + task.getAssigneeId()
                    + " не относится к команде организаторов");
        if (organizers.stream().noneMatch(x -> x.getUserId() == task.getAuthorId()))
            throw new ConflictException("Пользователь с id = " + task.getAssigneeId()
                    + " не относится к команде организаторов");
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
