package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.exeptions.ConflictException;
import org.example.exeptions.StorageException;
import org.modelmapper.ModelMapper;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private ModelMapper mapper;
    private LocalDateTime time = LocalDateTime.now();


    public TaskDto createTask(NewTaskDto newTask) {
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
        return mapper.map(taskRepository.save(task), TaskDto.class);
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
        return mapper.map(taskRepository.save(task), TaskDto.class);
    }

    public TaskDto getTask(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        return mapper.map(task, TaskDto.class);
    }

    public List<TaskDto> getTasks(int page,int size, Long eventId, Long assigneeId, Long authorId) {
        Pageable paged = PageRequest.of(page, size);
//        QTask qTask = QTask.task;
        return taskRepository.findAll().stream().
                map(x->mapper.map(x, TaskDto.class))
                .collect(Collectors.toList());
    }

    public void delete(Long userId, long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new StorageException("Событие не найдено или недоступно"));
        if (!userId.equals(task.getAuthorId())){
            throw new ConflictException("Нет прав на удаление задачи");
        }
        taskRepository.deleteById(id);
    }
}
