package ru.practicum.exploreWithMe.participationRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.participationRequest.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Long> {
    @Query(value = "select count(PR.id) from ParticipationRequest PR where PR.eventId = ?1 and PR.status = ?2")
    long getConfirmedRequests(long eventId, String status);

    boolean existsByRequesterIdAndEventId(long requestorId, long eventId);

    List<ParticipationRequest> getAllByRequesterId(long userId);

    @Query(value = "select PR from ParticipationRequest PR where PR.eventId = ?1")
    List<ParticipationRequest> getAllByEventId(long eventId);

    List<ParticipationRequest> getAllByStatusAndEventId(String status, long eventId);
}
