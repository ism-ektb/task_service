package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(of ={"id"})
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDateTime deadline;
    private String status;
    private Long assigneeId;
    private Long authorId;
    private Long eventId;
}
