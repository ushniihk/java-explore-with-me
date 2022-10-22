package ru.practicum.exploreWithMe.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.location.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateEventRequest {
    private String annotation;
    private long category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
}
