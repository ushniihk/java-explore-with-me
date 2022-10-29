package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private long category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private long eventId;
    private boolean paid;
    private int participantLimit;
    @Size(min = 3, max = 120)
    private String title;
}
