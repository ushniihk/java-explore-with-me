package ru.practicum.exploreWithMe.hit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = "select h from Hit h where h.timestamp >= ?1 and h.timestamp <= ?2")
    List<Hit> geAllByTime(LocalDateTime start, LocalDateTime end);

    @Query(value = "select h from Hit h where h.timestamp >= ?1 and h.timestamp <= ?2 and h.uri in ?3")
    List<Hit> geAllByTimeAndUri(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query(value = "select count(h.id) from Hit h where h.uri = ?1")
    long getContHit(String uri);
}
