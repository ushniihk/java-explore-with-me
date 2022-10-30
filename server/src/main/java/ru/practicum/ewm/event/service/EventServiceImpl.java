package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.IncorrectParameterException;
import ru.practicum.ewm.exceptions.UpdateException;
import ru.practicum.ewm.location.dto.LocationMapper;
import ru.practicum.ewm.location.repository.LocationRepository;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDTO;
import ru.practicum.ewm.participationRequest.dto.RequestMapper;
import ru.practicum.ewm.participationRequest.model.Status;
import ru.practicum.ewm.participationRequest.repository.ParticipationRequestsRepository;
import ru.practicum.ewm.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<EventFullDto> getAllPublished(String text, List<Long> categories, boolean paid, String rangeStart,
                                              String rangeEnd, String sort, boolean onlyAvailable, int from, int size) {
        if (sort.equals("EVENT_DATE")) {
            sort = "eventDate";
        } else {
            sort = "views";
        }
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(sort).ascending());
        List<EventFullDto> list =
                filterByDate(eventRepository.getEventByStateAndCategoryInAndPaid(State.PUBLISHED.toString(), categories, paid, pageRequest)
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
    public EventFullDto adminUpdate(long eventId, UpdateEventRequest eventDto) {
        checkEventId(eventId);
        Event event = eventMapper.toEvent(eventDto, eventId);
        Event eventOld = eventRepository.getReferenceById(eventId);
        event.setRequestModeration(eventOld.isRequestModeration());
        event.setLocation(eventOld.getLocation());
        event.setCreatedOn(eventOld.getCreatedOn());
        event.setInitiator(eventOld.getInitiator());
        event.setState(eventOld.getState());
        event.setViews(eventOld.getViews());
        if (event.getState().equals("PUBLISHED")) {
            event.setPublishedOn(eventOld.getPublishedOn());
            eventRepository.save(event);
            return eventMapper.toEventFullDtoPublished(event);
        }
        eventRepository.save(event);
        return eventMapper.toEventFullDtoNotPublished(event);
    }

    @Override
    @Transactional
    public ParticipationRequestDTO confirm(long userId, long eventId, long reqId) {
        checkUserId(userId);
        checkRequestId(reqId);
        checkEventId(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getParticipantLimit() == requestsRepository
                .countParticipationRequestByEventIdAndStatus(eventId, Status.CONFIRMED.toString()))
            throw new UpdateException("all seats are occupied");
        ParticipationRequest request = requestsRepository.getReferenceById(reqId);
        if (request.getStatus().equals(Status.CONFIRMED.toString()))
            throw new UpdateException("request has already been confirmed");
        request.setStatus(Status.CONFIRMED.toString());
        requestsRepository.save(request);
        if (event.getParticipantLimit() == requestsRepository
                .countParticipationRequestByEventIdAndStatus(eventId, Status.CONFIRMED.toString()))
            requestsRepository.getAllByStatusAndEventId(Status.PENDING.toString(), eventId)
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
        request.setStatus(Status.REJECTED.toString());
        requestsRepository.save(request);
        return RequestMapper.toDto(request);
    }

    @Override
    @Transactional
    public EventFullDto publish(long eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        checkBeforeChangeState(event);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1)))
            throw new UpdateException("event will start in less than an hour");
        event.setPublishedOn(LocalDateTime.now());
        event.setState(State.PUBLISHED.toString());
        eventRepository.save(event);
        return eventMapper.toEventFullDtoPublished(event);
    }

    @Override
    @Transactional
    public EventFullDto adminReject(long eventId) {
        Event event = eventRepository.getReferenceById(eventId);
        checkBeforeChangeState(event);
        event.setState(State.CANCELED.toString());
        eventRepository.save(event);
        return eventMapper.toEventFullDtoNotPublished(event);
    }

    @Override
    public List<EventFullDto> getAllByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                            String rangeStart, String rangeEnd, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return filterByDate(eventRepository.getEventsByStateInAndCategoryInAndInitiatorIn(states, categories, users, pageRequest).toList(), rangeStart, rangeEnd)
                .stream().map(eventMapper::toEventFullDtoPublished).collect(Collectors.toList());
    }

    private void checkDate(LocalDateTime time) {
        if (!time.isAfter(LocalDateTime.now().plusHours(2)))
            throw new IncorrectParameterException("time is too early");
    }

    private void checkUserId(long userId) {
        if (!userRepository.existsById(userId))
            throw new IncorrectParameterException("bad userId");
    }

    private void checkEventId(Long eventId) {
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

    private void checkBeforeChangeState(Event event) {
        if (!event.getState().equals(State.PENDING.toString()))
            throw new UpdateException("Event is not pending");
    }

}
