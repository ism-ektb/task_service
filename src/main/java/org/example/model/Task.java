package org.example.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "created")
    private LocalDateTime createdDateTime;
    @Column(name = "deadline")
    private LocalDateTime deadline;
    @Column(name = "status")
    private String status;
    @Column(name = "assignee_id")
    private Long assigneeId;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "event_id")
    private Long eventId;
}
