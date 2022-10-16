package ru.practicum.exploreWithMe.event.service;

import ru.practicum.exploreWithMe.event.model.EventFullDto;
import ru.practicum.exploreWithMe.event.model.EventShortDto;
import ru.practicum.exploreWithMe.event.model.NewEventDto;
import ru.practicum.exploreWithMe.event.model.UpdateEventRequest;

import java.util.List;

public interface EventService {
    EventFullDto get(long userId, long eventId);

    List<EventShortDto> getAll(long userId, int from, int size);
    List<EventShortDto> getAllByUser(long userId, int from, int size);


    NewEventDto add(long userId, NewEventDto eventDto);

    EventFullDto cancel(long userId, long eventId);

    EventFullDto update(long userId, UpdateEventRequest eventDto);

    NewEventDto confirm(long userId, long eventId, long reqId);

    NewEventDto reject(long userId, long eventId, long reqId);
}
