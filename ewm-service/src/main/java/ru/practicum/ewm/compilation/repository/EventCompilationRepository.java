package ru.practicum.ewm.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.compilation.model.EventCompilation;

import java.util.Optional;

public interface EventCompilationRepository extends JpaRepository<EventCompilation, Long> {
    Optional<EventCompilation> getByEventIdAndCompilationId(long compId, long eventId);

    boolean existsByCompilationIdAndEventId(long compId, long eventId);

}
