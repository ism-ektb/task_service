package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность Новая задача")
public class NewTaskDto {

    @Schema(description = "Название", example = "Добавить readme в проект")
    private String title;

    @Schema(description = "Описание", example = "В readme кратко описать назначение проекта")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")


    @Schema(description = "Дедлайн", example = "2024-12-03T10:15:30")

    private LocalDateTime deadline;

    @Schema(description = "Идентификатор исполнителя", example = "5")
    private Long assigneeId;

    @Schema(description = "Идентификатор автора", example = "4")
    private Long authorId;

    @Schema(description = "Идентификатор события", example = "2")
    private Long eventId;
}
