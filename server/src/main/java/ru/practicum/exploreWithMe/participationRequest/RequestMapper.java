package ru.practicum.exploreWithMe.participationRequest;


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

    public static ParticipationRequest toRequest(ParticipationRequestDTO dto) {
        return new ParticipationRequest(
                dto.getId(),
                dto.getCreated(),
                dto.getEventId(),
                dto.getRequesterId(),
                dto.getStatus()
        );
    }
}
