package ru.practicum.ewm.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "MESSAGE", nullable = false)
    private String message;
    @Column(name = "EVENT_ID", nullable = false)
    private long eventId;
    @Column(name = "USER_ID", nullable = false)
    private long userId;
    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;

    public Comment(String message, long eventId, long userId, LocalDateTime created) {
        this.message = message;
        this.eventId = eventId;
        this.userId = userId;
        this.created = created;
    }
}
