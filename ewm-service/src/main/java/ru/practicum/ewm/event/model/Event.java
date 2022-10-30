package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
@Entity
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "annotation", nullable = false)
    private String annotation;
    @Column(name = "category")
    private long category;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "EVENT_DATE", nullable = false)
    private LocalDateTime eventDate;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "initiator")
    private long initiator;
    @Column(name = "location")
    private long location;
    @Column(name = "paid")
    private boolean paid;
    @Column(name = "participant_limit")
    private long participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    @Column(name = "state")
    private String state;
    @Column(name = "title")
    private String title;
    @Column(name = "VIEWS")
    private long views;

    public Event(String annotation, long category, String description, LocalDateTime eventDate, long location, boolean paid,
                 long participantLimit, boolean requestModeration, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
    }

    public Event(long eventId, String annotation, long category, String description, LocalDateTime eventDate, boolean paid,
                 long participantLimit, String title) {
        this.id = eventId;
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.title = title;

    }
}
