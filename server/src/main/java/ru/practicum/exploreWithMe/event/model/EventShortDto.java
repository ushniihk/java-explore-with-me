package ru.practicum.exploreWithMe.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.category.model.CategoryDto;
import ru.practicum.exploreWithMe.user.model.UserShortDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private long views;

    public EventShortDto(String annotation, CategoryDto category, long confirmedRequests, String eventDate, long id,
                         UserShortDto userShortDto, boolean paid, String title) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = eventDate;
        this.id = id;
        this.initiator = userShortDto;
        this.paid = paid;
        this.title = title;
    }
}
