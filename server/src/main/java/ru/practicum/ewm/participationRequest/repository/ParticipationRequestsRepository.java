package ru.practicum.ewm.participationRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Long> {
    long countParticipationRequestByEventIdAndStatus(long eventId, String status);

    boolean existsByRequesterIdAndEventId(long requestorId, long eventId);

    List<ParticipationRequest> getAllByRequesterId(long userId);

    List<ParticipationRequest> getAllByEventId(long eventId);

    List<ParticipationRequest> getAllByStatusAndEventId(String status, long eventId);
}
