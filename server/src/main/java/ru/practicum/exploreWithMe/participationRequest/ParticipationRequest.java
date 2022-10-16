package ru.practicum.exploreWithMe.participationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participationRequests")
@Entity
public class ParticipationRequest {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "eventId", nullable = false)
    private long eventId;
    @Column(name = "requesterId", nullable = false)
    private long requesterId;
    @Column(name = "status", nullable = false)
    private String status;

    public ParticipationRequest(LocalDateTime created, long eventId, long requesterId, String status) {
        this.created = created;
        this.eventId = eventId;
        this.requesterId = requesterId;
        this.status = status;
    }
}
