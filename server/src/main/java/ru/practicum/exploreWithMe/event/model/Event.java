package ru.practicum.exploreWithMe.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @Column(name = "eventDate", nullable = false)
    private LocalDateTime eventDate;
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    @Column(name = "initiator")
    private long initiator;
    @Column(name = "location")
    private long location;
    @Column(name = "paid")
    private boolean paid;
    @Column(name = "participantLimit")
    private int participantLimit;
    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;
    @Column(name = "requestModeration")
    private boolean requestModeration;
    @Column(name = "state")
    private String state;
    @Column(name = "title")
    private String title;
    @Column(name = "views")
    private long views;

    public Event(String annotation, long category, String description, LocalDateTime eventDate, long location, boolean paid,
                 int participantLimit, boolean requestModeration, String title) {
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
                 int participantLimit, String title) {
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
