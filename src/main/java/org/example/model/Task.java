package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;


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
    @Builder.Default
    private LocalDateTime createdDateTime = LocalDateTime.now();
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
    @ManyToOne
    @JoinColumn(name = "epic_id", referencedColumnName = "id")
    private Epic epic;
}
