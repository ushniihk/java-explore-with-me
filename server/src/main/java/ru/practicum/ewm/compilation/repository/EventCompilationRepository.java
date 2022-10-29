package ru.practicum.ewm.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.compilation.model.EventCompilation;

import java.util.Optional;

public interface EventCompilationRepository extends JpaRepository<EventCompilation, Long> {
    @Query(value = "select e from EventCompilation e where e.compilationId = ?1 and e.eventId = ?2")
    Optional<EventCompilation> getByEventIdAndCompilationId(long compId, long eventId);

    boolean existsByCompilationIdAndEventId(long compId, long eventId);

}
