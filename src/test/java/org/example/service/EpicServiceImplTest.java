package org.example.service;

import org.example.dto.*;
import org.example.exeptions.ConflictException;
import org.example.exeptions.StorageException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.bouncycastle.util.StoreException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EpicServiceImplTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private EpicService epicService;
    @Autowired
    private TaskService taskService;

    @Test
    @Order(1)
    void create_goodResult() {
        EpicInDto epicInDto = EpicInDto.builder().name("Группа")
                .deadline(LocalDateTime.now().plusDays(1L))
                .eventId(1L)
                .responsibleId(1L).build();
        EpicOutDto epicOutDto = epicService.create(epicInDto);
        assertEquals(epicOutDto.getName(), "Группа");

    }

    @Test
    @Order(2)
    void create_noEventId_badResult() {
        EpicInDto epicInDto = EpicInDto.builder().name("Группа")
                .deadline(LocalDateTime.now().plusDays(1L))
                .responsibleId(1L)
                .build();
        assertThrows(NullPointerException.class, () -> epicService.create(epicInDto));


    }

    @Test
    @Order(3)
    void patch_badResult() {
        EpicPatchDto epicPatchDto = EpicPatchDto.builder().name("Group").build();
        assertThrows(StorageException.class, () -> epicService.patch(10L, epicPatchDto));
    }

    @Test
    @Order(4)
    void patch_goodResult() {
        EpicPatchDto epicPatchDto = EpicPatchDto.builder().name("Group").responsibleId(1L).build();
        EpicOutLiteDto epicOutDto = epicService.patch(1L, epicPatchDto);
        assertEquals(epicOutDto.getName(), "Group");
    }

    @Test
    @Order(5)
    void addTask() {
        NewTaskDto newTaskDto = new NewTaskDto("Task", "description", LocalDateTime.now().plusDays(1L), 1L, 1L, 1L);
    taskService.createTask(newTaskDto);
    assertThrows(StorageException.class, () -> epicService.addTask(10L, 1L, 1L));
    epicService.addTask(1L, 1L, 1L);

    }


    @Test
    @Order(6)
    void TestAddTask() {
        EpicOutDto epicOutDto = epicService.findById(1L);
        assertEquals(epicOutDto.getTasks().get(0).getId(), 1L);
    }

    @Test
    @Order(7)
    void deleteTask() {
        TaskDto task = taskService.createTask(new NewTaskDto("TaskTest", "description111", LocalDateTime.now().plusDays(3L), 1L, 1L, 1L));
        epicService.addTask(1L, 1L, task.getId());
        epicService.deleteTask(1L, 1L, task.getId());
    }

    @Test
    @Order(8)
    void findById() {
        EpicOutDto epicOutDto = epicService.findById(1L);
        assert(epicOutDto.getTasks().size() <= 2);
    }
}