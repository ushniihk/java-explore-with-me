package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.user.dto.UserShortDto;

@Data
@AllArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private long participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private long views;

    public EventFullDto(String annotation, CategoryDto category, long confirmedRequests, String createdOn,
                        String description, String eventDate, long id, UserShortDto initiator, LocationDto location,
                        boolean paid, long participantLimit, boolean requestModeration, String state, String title,
                        long views) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.id = id;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.views = views;
    }
}
