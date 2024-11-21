package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.TaskController;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {
    @MockBean
    private TaskServiceImpl taskService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    private TaskDto taskDto;
    private LocalDateTime now = LocalDateTime.now();


    @BeforeEach
    void setUp() {
        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("задача 1");
        taskDto.setDescription("создание новой задачи");
        taskDto.setCreatedDateTime(now);
        taskDto.setDeadline(LocalDateTime.now().plusMonths(2));
        taskDto.setStatus("New");
        taskDto.setAssigneeId(1L);
        taskDto.setEventId(1L);
        taskDto.setAuthorId(2L);
    }

    @Test
    void createTaskTest() throws Exception {
        when(taskService.createTask(any(NewTaskDto.class)))
                .thenReturn(taskDto);

        mvc.perform(post("/tasks")
                        .content(objectMapper.writeValueAsString(taskDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskDto.getId()), Long.class))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()));
        verify(taskService, times(1)).createTask(any(NewTaskDto.class));
    }

    @Test
    void updateTaskTest() throws Exception {
        when(taskService.updateTask(anyLong(), anyLong(), any(UpdateTaskDto.class))).thenReturn(taskDto);
        mvc.perform(patch("/tasks/1")
                        .content(objectMapper.writeValueAsString(taskDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Task-User-Id", "1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskDto.getId()), Long.class))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.authorId", is(taskDto.getAuthorId()), Long.class));
        verify(taskService, times(1)).updateTask(anyLong(), anyLong(), any(UpdateTaskDto.class));
    }

    @Test
    void getTaskTest() throws Exception {
        when(taskService.getTask(anyLong())).thenReturn(taskDto);
        mvc.perform(get("/tasks/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .header("X-Task-User-Id", "1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskDto.getId()), Long.class))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.authorId", is(taskDto.getAuthorId()), Long.class));
        verify(taskService, times(1)).getTask(anyLong());
    }

    @Test
    void getTasksTest() throws Exception {
        List<TaskDto> tasks = List.of(taskDto);
        when(taskService.getTasks(anyInt(),anyInt(),anyLong(),anyLong(),anyLong())).thenReturn(tasks);
        mvc.perform(get("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Task-User-Id", "1")
                .param("page", "1")
                .param("size", "2")
                .param("eventId","1")
                .param("assignTo","1" )
                .param("authorId","2")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(taskDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$[0].description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$[0].authorId", is(taskDto.getAuthorId()), Long.class))
                .andExpect(jsonPath("$[0].authorId").value(taskDto.getAuthorId()))
                .andExpect(jsonPath("$[0].authorId").value(2));
        verify(taskService, times(1)).getTasks(anyInt(),anyInt(),anyLong(),anyLong(),anyLong());
    }
}
