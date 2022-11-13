package ru.practicum.ewm.participationRequest.dto;


import ru.practicum.ewm.participationRequest.model.ParticipationRequest;

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
