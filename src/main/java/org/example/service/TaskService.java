package org.example.service;

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

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;
    private final TaskListMapper listMapper;


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

    public TaskDto updateTask(Long userId, Long id, UpdateTaskDto updateTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        if (!userId.equals(task.getAuthorId()) || !userId.equals(task.getAssigneeId())){
            throw new ConflictException("Нет прав на изменение задачи");
        }
        task.setTitle(updateTask.getTitle());
        task.setDescription(updateTask.getDescription());
        task.setDeadline(updateTask.getDeadline());
        task.setEventId(updateTask.getEventId());
        return mapper.modelToDto(taskRepository.save(task));
    }

    public TaskDto getTask(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        return mapper.modelToDto(task);
    }

    public List<TaskDto> getTasks(int page,int size, Long eventId, Long assigneeId, Long authorId) {
        Pageable paged = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findByFilters(assigneeId, eventId, authorId,paged);
        return listMapper.modelsToDtos(tasks.toList());
    }

    public void delete(Long userId, long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        if (!userId.equals(task.getAuthorId())){
            throw new ConflictException("Нет прав на удаление задачи");
        }
        taskRepository.deleteById(id);
    }
}
