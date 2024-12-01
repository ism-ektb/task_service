package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(of ={"id"})
@Schema(description = "Сущность Задача")
public class TaskDto {

    @Schema(description = "Идентификатор задачи", example = "6")
    private Long id;

    @Schema(description = "Название", example = "Добавить readme в проект")
    private String title;

    @Schema(description = "Описание", example = "В readme кратко описать назначение проекта")
    private String description;

    @Schema(description = "Дата создания", example = "2024-12-01T10:15:30")
    private LocalDateTime createdDateTime;

    @Schema(description = "Дедлайн", example = "2024-12-03T10:15:30")
    private LocalDateTime deadline;

    @Schema(description = "Статус задачи", allowableValues = {"PENDING", "APPROVED"})
    private String status;

    @Schema(description = "Идентификатор исполнителя", example = "5")
    private Long assigneeId;

    @Schema(description = "Идентификатор автора", example = "4")
    private Long authorId;

    @Schema(description = "Идентификатор события", example = "2")
    private Long eventId;
}
