package ru.practicum.ewm.hit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.hit.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {
    List<Hit> getHitsByTimestampAfterAndTimestampBefore(LocalDateTime start, LocalDateTime end);

    List<Hit> getHitsByTimestampAfterAndTimestampBeforeAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uri);

    long countHitsByUri(String uri);
}
