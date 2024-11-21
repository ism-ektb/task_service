package org.example.service;

import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicPatchDto;
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
                .responsibleId(1L).build();
        assertThrows(DataIntegrityViolationException.class, () -> epicService.create(epicInDto));


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
        EpicPatchDto epicPatchDto = EpicPatchDto.builder().name("Group").build();
        EpicOutDto epicOutDto = epicService.patch(1L, epicPatchDto);
        assertEquals(epicOutDto.getName(), "Group");
    }

    @Test
    void addTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void findById() {
    }
}