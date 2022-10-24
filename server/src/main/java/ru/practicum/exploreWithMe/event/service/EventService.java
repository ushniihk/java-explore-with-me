package ru.practicum.exploreWithMe.event.service;

import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.event.dto.UpdateEventRequest;
import ru.practicum.exploreWithMe.participationRequest.dto.ParticipationRequestDTO;

import java.util.List;

public interface EventService {
    EventFullDto get(long userId, long eventId);

    EventFullDto getPublished(long eventId);

    List<EventFullDto> getAllPublished(String text, List<Long> categories, boolean paid, String rangeStart,
                                       String rangeEnd, String sort, boolean onlyAvailable, int from, int size);

    List<EventShortDto> getAllByUser(long userId, int from, int size);

    List<ParticipationRequestDTO> getRequestsForEvent(long userId, long eventId);

    EventFullDto add(long userId, NewEventDto eventDto);

    EventFullDto cancel(long userId, long eventId);

    EventFullDto update(long userId, UpdateEventRequest eventDto);

    EventFullDto adminUpdate(long eventId, UpdateEventRequest eventDto);


    ParticipationRequestDTO confirm(long userId, long eventId, long reqId);

    ParticipationRequestDTO reject(long userId, long eventId, long reqId);

    EventFullDto publish(long eventId);

    EventFullDto adminReject(long eventId);

    List<EventFullDto> getAllByAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                     String rangeEnd, int from, int size);
}
