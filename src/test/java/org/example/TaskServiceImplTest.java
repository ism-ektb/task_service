package org.example;

import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.exeptions.StorageException;
import org.example.mapper.TaskMapper;
import org.example.model.Task;
import org.example.service.TaskServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceImplTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private TaskServiceImpl taskService;
    private Task task;
    @Autowired
    private TaskMapper mapper;
    private TaskDto taskDto;
    private LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {

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
        taskDto.setEventId(task.getEventId());
        taskDto.setAuthorId(task.getAuthorId());
    }

    @Test
    @Order(1)
    void testCreate() {
        NewTaskDto newTask = new NewTaskDto();
        newTask.setTitle(task.getTitle());
        newTask.setDescription("создание новой задачи");
        newTask.setDeadline(task.getDeadline());
        newTask.setAssigneeId(task.getAssigneeId());
        newTask.setAuthorId(task.getAuthorId());
        newTask.setEventId(task.getEventId());
        TaskDto result = taskService.createTask(newTask);

        assertEquals(taskDto, result);
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getDescription(), result.getDescription());
        assertEquals(newTask.getAuthorId(),result.getAuthorId());
    }

    @Test
    @Order(2)
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
        TaskDto result = taskService.updateTask(1L, 1L, updateTask);
        assertEquals(mapper.modelToDto(task), result);
    }

    @Test
    @Order(3)
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
    @Order(4)
    void getTasksTest(){
        List<TaskDto> listDto = new ArrayList<>();
        listDto.add(taskDto);
        List<Task> list = new ArrayList<>();
        list.add(task);
        Page<Task> tasks = new PageImpl<>(list);
        Pageable page = PageRequest.of(0, 10);
        List<TaskDto> result = taskService.getTasks(0, 1, 1L, 1L, 2L);

        assertEquals(1, result.size());
        assertEquals(task.getId(), result.get(0).getId());
    }
}
