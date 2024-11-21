package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
@Data
public class NewTaskDto {

    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long assigneeId;
    private Long authorId;
    private Long eventId;
}
