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
import ru.practicum.exploreWithMe.participationRequest.*;
import ru.practicum.exploreWithMe.user.model.UserMapper;
import ru.practicum.exploreWithMe.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final ParticipationRequestsRepository requestsRepository;

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
    @Transactional
    public EventFullDto getPublished(long eventId) {
        checkEventId(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        EventFullDto dto = eventMapper.toEventFullDtoPublished(event);
        if (!event.getState().equals("PUBLISHED"))
            throw new IncorrectParameterException("event has not been published yet");
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
        return dto;
    }


    @Override
    public List<EventFullDto> getAllPublished(String text, String[] categories, boolean paid, String rangeStart,
                                              String rangeEnd, String sort, boolean onlyAvailable, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(sort).ascending());
        List<EventFullDto> list =
                filterByDate(eventRepository.getAllPublished(State.PUBLISHED.toString(), categories, paid, pageRequest)
                        .stream().collect(Collectors.toList()), rangeStart, rangeEnd).stream()
                        .filter(event -> event.getAnnotation().toLowerCase().contains(text.toLowerCase())
                                || event.getDescription().toLowerCase().contains(text.toLowerCase()))
                        .map(eventMapper::toEventFullDtoPublished).collect(Collectors.toList());
        if (!onlyAvailable)
            return list;
        return list.stream().filter(event -> event.getConfirmedRequests() < event.getParticipantLimit())
                .collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> getAllByUser(long userId, int from, int size) {
        checkUserId(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return eventRepository.getAllByInitiator(userId, pageRequest).stream()
                .map(eventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public List<ParticipationRequestDTO> getRequestsForEvent(long userId, long eventId) {
        checkEventId(eventId);
        checkUserId(userId);
        return requestsRepository.getAllByEventId(eventId).stream()
                .map(RequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto add(long userId, NewEventDto eventDto) {
        checkUserId(userId);
        locationRepository.save(LocationMapper.toLocation(eventDto.getLocation()));
        Event event = eventMapper.toEvent(eventDto);
        checkDate(event.getEventDate());
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(userId);
        event.setState(State.PENDING.toString());
        event.setViews(0);
        return eventMapper.toEventFullDtoNotPublished(eventRepository.save(event));
    }

    @Override
    @Transactional
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
    public EventFullDto adminUpdate(long eventId, AdminUpdateEventRequest eventDto) {
        checkEventId(eventId);
        Event event = eventMapper.toEvent(eventDto, eventId);
        Event eventOld = eventRepository.getReferenceById(eventId);
        event.setCreatedOn(eventOld.getCreatedOn());
        event.setInitiator(eventOld.getInitiator());
        event.setState(eventOld.getState());
        event.setViews(eventOld.getViews());
        eventRepository.save(event);
        if (eventOld.getState().equals("PUBLISHED"))
            return eventMapper.toEventFullDtoPublished(event);
        return eventMapper.toEventFullDtoNotPublished(event);
    }

    @Override
    @Transactional
    public ParticipationRequestDTO confirm(long userId, long eventId, long reqId) {
        checkUserId(userId);
        checkRequestId(reqId);
        checkEventId(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getParticipantLimit() == requestsRepository.getConfirmedRequests(eventId, Status.CONFIRMED.toString()))
            throw new UpdateException("all seats are occupied");
        ParticipationRequest request = requestsRepository.getReferenceById(reqId);
        if (request.getStatus().equals(Status.CONFIRMED.toString()))
            throw new UpdateException("request has already been confirmed");
        request.setStatus(Status.CONFIRMED.toString());
        requestsRepository.save(request);
        if (event.getParticipantLimit() == requestsRepository.getConfirmedRequests(eventId, Status.CONFIRMED.toString()))
            requestsRepository.getAllByStatusAndEventId(eventId, Status.PENDING.toString())
                    .forEach(req -> reject(userId, eventId, req.getId()));
        return RequestMapper.toDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDTO reject(long userId, long eventId, long reqId) {
        checkEventId(eventId);
        checkUserId(userId);
        checkRequestId(reqId);
        ParticipationRequest request = requestsRepository.getReferenceById(reqId);
        request.setStatus(Status.CANCELED.toString());
        requestsRepository.save(request);
        return RequestMapper.toDto(request);
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

    private void checkRequestId(long reqId) {
        if (!requestsRepository.existsById(reqId))
            throw new IncorrectParameterException("bad requestId");
    }

    private List<Event> filterByDate(List<Event> events, String start, String end) {
        if (start == null && end == null) {
            return events.stream()
                    .filter(event -> event.getEventDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startTime = LocalDateTime.parse(start, dtf);
        LocalDateTime endTime = LocalDateTime.parse(end, dtf);
        return events.stream().filter(event -> event.getEventDate().isAfter(startTime))
                .filter(event -> event.getEventDate().isBefore(endTime)).collect(Collectors.toList());
    }

}
