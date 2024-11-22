package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "Дто для создания Эпика")
@Jacksonized
public class EpicInDto {
    @NotBlank(message = "Имя Эпика не должно быть пустым")
    @Schema(name = "Название Эпика")
    private String name;
    @Schema(name = "Id ответственного за эпик")
    @NotNull(message = "Поле ответственный не должно быть пустым")
    private Long responsibleId;
    @Schema(name = "Id Эвента")
    @NotNull(message = "Поле эвента не должно быть пустым")
    private Long eventId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "Дедлайн Эвента")
    @Future(message = "Дедлайн должен быть в будущем")
    private LocalDateTime deadline;
}
