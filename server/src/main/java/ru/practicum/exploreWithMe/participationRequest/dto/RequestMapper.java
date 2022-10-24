package ru.practicum.exploreWithMe.participationRequest.dto;


import ru.practicum.exploreWithMe.participationRequest.model.ParticipationRequest;

public class RequestMapper {
    public static ParticipationRequestDTO toDto(ParticipationRequest request) {
        return new ParticipationRequestDTO(
                request.getId(),
                request.getCreated(),
                request.getEventId(),
                request.getRequesterId(),
                request.getStatus()
        );
    }
}
