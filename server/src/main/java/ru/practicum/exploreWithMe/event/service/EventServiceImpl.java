package ru.practicum.exploreWithMe.event.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.event.State;
import ru.practicum.exploreWithMe.event.model.*;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.exceptions.IncorrectParameterException;
import ru.practicum.exploreWithMe.exceptions.UpdateException;
import ru.practicum.exploreWithMe.location.LocationMapper;
import ru.practicum.exploreWithMe.location.LocationRepository;
import ru.practicum.exploreWithMe.user.model.UserMapper;
import ru.practicum.exploreWithMe.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Override
    public EventFullDto get(long userId, long eventId) {
        checkUserId(userId);
        checkEventId(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getState().equals("PUBLISHED")) {
            return eventMapper.toEventFullDtoPublished(event);
        } else {
            return eventMapper.toEventFullDtoNotPublished(event);
        }
    }

    @Override
    public List<EventShortDto> getAll(long userId, int from, int size) {
/*        checkUserId(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return eventRepository.getAllByInitiator(userId, pageRequest).stream()
                .map(eventMapper::toEventShortDto).collect(Collectors.toList());*/
        return null;
    }

    @Override
    public List<EventShortDto> getAllByUser(long userId, int from, int size) {
        checkUserId(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return eventRepository.getAllByInitiator(userId, pageRequest).stream()
                .map(eventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NewEventDto add(long userId, NewEventDto eventDto) {
        checkUserId(userId);
        locationRepository.save(LocationMapper.toLocation(eventDto.getLocation()));
        Event event = eventMapper.toEvent(eventDto);
        checkDate(event.getEventDate());
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(userId);
        event.setState(State.PENDING.toString());
        event.setViews(0);
        return eventMapper.toNewEventDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto cancel(long userId, long eventId) {
        checkEventId(eventId);
        checkUserId(userId);
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getInitiator() != userId)
            throw new IncorrectParameterException("you don't have event with this id");
        if (event.getState().equals("PUBLISHED"))
            throw new UpdateException("event has been already published");
        event.setState(State.CANCELED.toString());
        eventRepository.save(event);
        return eventMapper.toEventFullDtoNotPublished(event);
    }

    @Override
    @Transactional
    public EventFullDto update(long userId, UpdateEventRequest eventDto) {
        checkUserId(userId);
        checkEventId(eventDto.getEventId());
        Event event = eventMapper.toEvent(eventDto);
        Event eventOld = eventRepository.getReferenceById(eventDto.getEventId());
        if (eventOld.getState().equals("PUBLISHED"))
            throw new UpdateException("event has been already been published");
        checkDate(event.getEventDate());
        event.setLocation(eventOld.getLocation());
        event.setCreatedOn(eventOld.getCreatedOn());
        event.setInitiator(userId);
        event.setState(eventOld.getState());
        event.setViews(eventOld.getViews());
        eventRepository.save(event);
        return eventMapper.toEventFullDtoNotPublished(event);
    }

    @Override
    public NewEventDto confirm(long userId, long eventId, long reqId) {
        return null;
    }

    @Override
    public NewEventDto reject(long userId, long eventId, long reqId) {
        checkEventId(eventId);
        checkUserId(userId);
        if (!eventRepository.existsById(reqId))
            throw new IncorrectParameterException("bad reqID");
        return null;
    }

    private void checkDate(LocalDateTime time) {
        if (!time.isAfter(LocalDateTime.now().plusHours(2)))
            throw new IncorrectParameterException("time is too early");
    }

    private void checkUserId(long userId) {
        if (!userRepository.existsById(userId))
            throw new IncorrectParameterException("bad userId");
    }

    private void checkEventId(long eventId) {
        if (!eventRepository.existsById(eventId))
            throw new IncorrectParameterException("bad eventId");
    }
}
