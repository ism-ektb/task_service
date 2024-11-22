package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.exeptions.ConflictException;
import org.example.exeptions.StorageException;
import org.example.mapper.TaskListMapper;
import org.example.mapper.TaskMapper;

import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;
    private final TaskListMapper listMapper;


    @Override
    public TaskDto createTask(NewTaskDto newTask) {
        LocalDateTime time = LocalDateTime.now();
        Task task = Task.builder()
                .title(newTask.getTitle())
                .description(newTask.getDescription())
                .createdDateTime(time)
                .deadline(newTask.getDeadline())
                .status("New")
                .assigneeId(newTask.getAssigneeId())
                .authorId(newTask.getAuthorId())
                .eventId(newTask.getEventId())
                .build();
        return mapper.modelToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto updateTask(Long userId, Long id, UpdateTaskDto updateTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        if ((userId != task.getAuthorId()) && userId != task.getAssigneeId()){
            throw new ConflictException("Нет прав на изменение задачи");
        }
        task.setTitle(updateTask.getTitle());
        task.setDescription(updateTask.getDescription());
        task.setDeadline(updateTask.getDeadline());
        task.setStatus(updateTask.getStatus());
        task.setEventId(updateTask.getEventId());
        return mapper.modelToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto getTask(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        return mapper.modelToDto(task);
    }

    @Override
    public List<TaskDto> getTasks(int page, int size, Long eventId, Long assigneeId, Long authorId) {
        Pageable paged = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findByFilters(assigneeId, eventId, authorId,paged);
        if(tasks.isEmpty()){
            throw new StorageException("Задачи не найдены");
        }
        return listMapper.modelsToDtos(tasks.getContent());
    }

    @Override
    public void delete(Long userId, long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        if (!userId.equals(task.getAuthorId())){
            throw new ConflictException("Нет прав на удаление задачи");
        }
        taskRepository.deleteById(id);
    }
}
