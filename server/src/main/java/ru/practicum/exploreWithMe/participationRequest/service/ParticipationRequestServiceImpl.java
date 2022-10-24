package ru.practicum.exploreWithMe.participationRequest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.exceptions.IncorrectParameterException;
import ru.practicum.exploreWithMe.exceptions.UpdateException;
import ru.practicum.exploreWithMe.participationRequest.model.ParticipationRequest;
import ru.practicum.exploreWithMe.participationRequest.dto.ParticipationRequestDTO;
import ru.practicum.exploreWithMe.participationRequest.dto.RequestMapper;
import ru.practicum.exploreWithMe.participationRequest.model.Status;
import ru.practicum.exploreWithMe.participationRequest.repository.ParticipationRequestsRepository;
import ru.practicum.exploreWithMe.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestsRepository participationRequestsRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<ParticipationRequestDTO> getAllByRequestor(long userId) {
        checkUserId(userId);
        return participationRequestsRepository.getAllByRequesterId(userId).stream()
                .map(RequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDTO add(long userId, long eventId) {
        checkEventId(eventId);
        checkUserId(userId);
        Event event = eventRepository.getReferenceById(eventId);
        checkEvent(userId, event);
        ParticipationRequest request =
                new ParticipationRequest(LocalDateTime.now(), eventId, userId, Status.PENDING.toString());
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0)
            request.setStatus(Status.CONFIRMED.toString());
        participationRequestsRepository.save(request);
        return RequestMapper.toDto(request);
    }

    @Override
    public ParticipationRequestDTO cancel(long userId, long requestId) {
        checkUserId(userId);
        if (!participationRequestsRepository.existsById(requestId))
            throw new UpdateException("bad request Id");
        ParticipationRequest request = participationRequestsRepository.getReferenceById(requestId);
        request.setStatus(Status.CANCELED.toString());
        return RequestMapper.toDto(participationRequestsRepository.save(request));
    }

    private void checkEvent(long userId, Event event) {
        if (event.getInitiator() == userId)
            throw new UpdateException("you can't make request for your own event");
        if (!event.getState().equals("PUBLISHED"))
            throw new UpdateException("event has not been published yet");
        if (event.getParticipantLimit() == participationRequestsRepository.getConfirmedRequests(event.getId(), "CONFIRMED"))
            throw new UpdateException("there are no free seats for the event");
        if (participationRequestsRepository.existsByRequesterIdAndEventId(userId, event.getId()))
            throw new UpdateException("request has been already exist");
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
