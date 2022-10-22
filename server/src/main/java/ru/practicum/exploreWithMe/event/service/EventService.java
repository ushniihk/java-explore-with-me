package ru.practicum.exploreWithMe.event.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.exploreWithMe.event.model.*;
import ru.practicum.exploreWithMe.participationRequest.ParticipationRequestDTO;

import java.util.List;

public interface EventService {
    EventFullDto get(long userId, long eventId);

    EventFullDto getPublished(long eventId);

    List<EventFullDto> getAllPublished(String text, String[] categories, boolean paid, String rangeStart,
                                        String rangeEnd, String sort, boolean onlyAvailable, int from, int size);

    List<EventShortDto> getAllByUser(long userId, int from, int size);

    List<ParticipationRequestDTO> getRequestsForEvent(long userId, long eventId);

    EventFullDto add(long userId, NewEventDto eventDto);

    EventFullDto cancel(long userId, long eventId);

    EventFullDto update(long userId, UpdateEventRequest eventDto);

    EventFullDto adminUpdate(long eventId, AdminUpdateEventRequest eventDto);


    ParticipationRequestDTO confirm(long userId, long eventId, long reqId);

    ParticipationRequestDTO reject(long userId, long eventId, long reqId);
}
