package org.example;

import lombok.RequiredArgsConstructor;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.exeptions.ConflictException;
import org.example.exeptions.StorageException;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.example.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper mapper;
    private Task task;
    private TaskDto taskDto;
    private LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, mapper);
        task = Task.builder()
                .title("задача 1")
                .description("создание новой задачи")
                .createdDateTime(now)
                .deadline(LocalDateTime.now().plusMonths(2))
                .status("New")
                .assigneeId(1L)
                .authorId(2L)
                .eventId(1L)
                .build();
        task.setId(1L);
        taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setCreatedDateTime(task.getCreatedDateTime());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setStatus(task.getStatus());
        taskDto.setAssigneeId(task.getAssigneeId());
        taskDto.setEventId(task.getAuthorId());
        taskDto.setAuthorId(task.getAuthorId());
    }

    @Test
    void testCreate() {
        NewTaskDto newTask = new NewTaskDto();
        newTask.setTitle(task.getTitle());
        newTask.setDescription("создание новой задачи");
        newTask.setDeadline(task.getDeadline());
        newTask.setAssigneeId(task.getAssigneeId());
        newTask.setAuthorId(task.getAuthorId());
        newTask.setEventId(task.getEventId());
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(mapper.map(task, TaskDto.class)).thenReturn(taskDto);
        TaskDto result = taskService.createTask(newTask);

        assertEquals(taskDto, result);
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getDescription(), result.getDescription());
        assertEquals(newTask.getAuthorId(),result.getAuthorId());
    }

    @Test
    void testUpdate() {
        UpdateTaskDto updateTask = new UpdateTaskDto();
        updateTask.setTitle(task.getTitle());
        updateTask.setDescription("изменение описания задачи");
        updateTask.setDeadline(LocalDateTime.now().plusMonths(1));
        updateTask.setStatus("Update");
        updateTask.setAssigneeId(task.getAssigneeId());
        updateTask.setEventId(task.getEventId());
        Task newTask = Task.builder()
                .id(task.getId())
                .title(updateTask.getTitle())
                .description(updateTask.getDescription())
                .createdDateTime(task.getCreatedDateTime())
                .deadline(updateTask.getDeadline())
                .status(updateTask.getStatus())
                .assigneeId(updateTask.getAssigneeId())
                .authorId(task.getAuthorId())
                .eventId(updateTask.getEventId())
                .build();
        TaskDto newTaskDto = new TaskDto();
        newTaskDto.setId(newTask.getId());
        newTaskDto.setTitle(newTask.getTitle());
        newTaskDto.setDescription(newTask.getDescription());
        newTaskDto.setCreatedDateTime(newTask.getCreatedDateTime());
        newTaskDto.setDeadline(newTask.getDeadline());
        newTaskDto.setStatus(newTask.getStatus());
        newTaskDto.setAssigneeId(newTask.getAssigneeId());
        newTaskDto.setEventId(newTask.getEventId());
        newTaskDto.setAuthorId(newTask.getAuthorId());
        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);
        when(mapper.map(newTask, TaskDto.class)).thenReturn(newTaskDto);
        TaskDto result = taskService.updateTask(1L,1L, updateTask);

        assertNotEquals(taskDto, result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getDescription(), result.getDescription());
        assertEquals(task.getDeadline(), result.getDeadline());
        assertEquals(task.getAuthorId(), result.getAuthorId());
    }

    @Test
    void testUpdateWrongId(){
        UpdateTaskDto updateTask = new UpdateTaskDto();
        updateTask.setTitle(task.getTitle());
        updateTask.setDescription("изменение описания задачи");
        updateTask.setDeadline(LocalDateTime.now().plusMonths(1));
        updateTask.setStatus("Update");
        updateTask.setAssigneeId(task.getAssigneeId());
        updateTask.setEventId(task.getEventId());

        assertThrows(StorageException.class, () -> taskService.updateTask(1L, 3L, updateTask));
    }

    @Test
    void getTasksTest(){
        List<TaskDto> listDto = new ArrayList<>();
        listDto.add(taskDto);
        List<Task> list = new ArrayList<>();
        list.add(task);
        Page<Task> tasks = new PageImpl<>(list);
        Pageable page = PageRequest.of(0, 10);
        when(taskRepository.findByFilters(anyLong(),anyLong(),anyLong(),any(Pageable.class))).thenReturn(tasks);
        when(mapper.map(task, TaskDto.class)).thenReturn(taskDto);
        List<TaskDto> result = taskService.getTasks(0,1,1L,2L,1L);

        assertEquals(1, result.size());
        assertEquals(task.getId(), result.get(0).getId());
    }
}
