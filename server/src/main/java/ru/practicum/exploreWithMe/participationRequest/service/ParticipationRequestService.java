package ru.practicum.exploreWithMe.participationRequest.service;

import ru.practicum.exploreWithMe.event.model.NewEventDto;
import ru.practicum.exploreWithMe.participationRequest.ParticipationRequestDTO;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDTO> getAllByRequestor(long userId);

    ParticipationRequestDTO add(long userId, long eventId);

    void cancel(long userId, long requestId);
}
