package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicOutLiteDto;
import org.example.dto.EpicPatchDto;
import org.example.service.EpicService;
import org.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EpicControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EpicService service;
    @MockBean
    private TaskService service1;

    @Test
    @SneakyThrows
    void create_goodResult() {
        EpicInDto epicInDto = EpicInDto.builder().name("Group")
                .responsibleId(1L)
                .eventId(1L)
                .deadline(LocalDateTime.now().plusDays(1L)).build();
        when(service.create(any())).thenReturn(EpicOutDto.builder().build());
        mvc.perform(post("/epics")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(mapper.writeValueAsString(epicInDto))).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void patchEpic_goodResult() {
        EpicPatchDto epic = EpicPatchDto.builder().name("Group").build();
        when(service.patch(anyLong(), any())).thenReturn(EpicOutLiteDto.builder().build());
        mvc.perform(patch("/epics/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(mapper.writeValueAsString(epic))).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void addTasks() {
        when(service.addTask(anyLong(), anyLong(), any())).thenReturn(EpicOutLiteDto.builder().build());
        mvc.perform(patch("/epics/{id}/addTask", 1L)
                .param("task", "1")
                .header("X-Task-User-Id", "1")).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void deleteTasks() {
        when(service.deleteTask(anyLong(), anyLong(), anyLong())).thenReturn(EpicOutLiteDto.builder().build());
        mvc.perform(patch("/epics/{id}/deleteTask", 1L)
                .param("task", "1")
                .header("X-Task-User-Id", "1")).andExpect(status().isOk());
    }

    @Test
    void findEpicById() {
    }
}