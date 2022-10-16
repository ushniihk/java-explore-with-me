package ru.practicum.exploreWithMe.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.category.model.CategoryDto;
import ru.practicum.exploreWithMe.category.model.CategoryMapper;
import ru.practicum.exploreWithMe.category.repository.CategoryRepository;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.location.Location;
import ru.practicum.exploreWithMe.location.LocationDto;
import ru.practicum.exploreWithMe.location.LocationMapper;
import ru.practicum.exploreWithMe.location.LocationRepository;
import ru.practicum.exploreWithMe.participationRequest.ParticipationRequest;
import ru.practicum.exploreWithMe.participationRequest.ParticipationRequestsRepository;
import ru.practicum.exploreWithMe.participationRequest.Status;
import ru.practicum.exploreWithMe.user.model.UserMapper;
import ru.practicum.exploreWithMe.user.model.UserShortDto;
import ru.practicum.exploreWithMe.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Data
@Component
public class EventMapper {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipationRequestsRepository prRepository;

    public NewEventDto toNewEventDto(Event event) {
        return new NewEventDto(
                event.getId(),
                event.getAnnotation(),
                event.getCategory(),
                event.getDescription(),
                event.getEventDate().format(dtf),
                LocationMapper.toLocationDto(locationRepository.getReferenceById(event.getLocation())),
                event.isPaid(),
                event.getParticipantLimit(),
                event.isRequestModeration(),
                event.getTitle()
        );
    }

    public Event toEvent(UpdateEventRequest eventDto){
        return new Event(
                eventDto.getEventId(),
                eventDto.getAnnotation(),
                eventDto.getCategory(),
                eventDto.getDescription(),
                LocalDateTime.parse(eventDto.getEventDate(), dtf),
                eventDto.isPaid(),
                eventDto.getParticipantLimit(),
                eventDto.getTitle()
        );
    }

    public Event toEvent(NewEventDto eventDto) {
        return new Event(
                eventDto.getAnnotation(),
                eventDto.getCategory(),
                eventDto.getDescription(),
                LocalDateTime.parse(eventDto.getEventDate(), dtf),
                locationRepository.getLocationByLatAndLon(
                        eventDto.getLocation().getLat(), eventDto.getLocation().getLon()).getId(),
                eventDto.isPaid(),
                eventDto.getParticipantLimit(),
                eventDto.isRequestModeration(),
                eventDto.getTitle()
        );
    }

    public EventFullDto toEventFullDtoNotPublished(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(categoryRepository.getReferenceById(event.getCategory())),
                prRepository.getConfirmedRequests(event.getId(), Status.CONFIRMED.toString()),
                dtf.format(event.getCreatedOn()),
                event.getDescription(),
                dtf.format(event.getEventDate()),
                event.getId(),
                UserMapper.toUserShortDto(userRepository.getReferenceById(event.getInitiator())),
                LocationMapper.toLocationDto(locationRepository.getReferenceById(event.getLocation())),
                event.isPaid(),
                event.getParticipantLimit(),
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public EventFullDto toEventFullDtoPublished(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(categoryRepository.getReferenceById(event.getCategory())),
                prRepository.getConfirmedRequests(event.getId(), Status.CONFIRMED.toString()),
                dtf.format(event.getCreatedOn()),
                event.getDescription(),
                dtf.format(event.getEventDate()),
                event.getId(),
                UserMapper.toUserShortDto(userRepository.getReferenceById(event.getInitiator())),
                LocationMapper.toLocationDto(locationRepository.getReferenceById(event.getLocation())),
                event.isPaid(),
                event.getParticipantLimit(),
                dtf.format(event.getPublishedOn()),
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(categoryRepository.getReferenceById(event.getCategory())),
                prRepository.getConfirmedRequests(event.getId(), Status.CONFIRMED.toString()),
                dtf.format(event.getEventDate()),
                event.getId(),
                UserMapper.toUserShortDto(userRepository.getReferenceById(event.getInitiator())),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }
}
