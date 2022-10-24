package ru.practicum.exploreWithMe.participationRequest.service;

import ru.practicum.exploreWithMe.participationRequest.dto.ParticipationRequestDTO;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDTO> getAllByRequestor(long userId);

    ParticipationRequestDTO add(long userId, long eventId);

    ParticipationRequestDTO cancel(long userId, long requestId);
}
