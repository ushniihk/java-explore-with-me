package ru.practicum.ewm.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events_compilation")
@Entity
public class EventCompilation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "EVENT_ID")
    private long eventId;
    @Column(name = "compilation_id")
    private long compilationId;

    public EventCompilation(long eventId, long compilationId) {
        this.eventId = eventId;
        this.compilationId = compilationId;
    }
}
