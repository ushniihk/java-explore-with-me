package ru.practicum.exploreWithMe.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> getAllByInitiator(long userId, PageRequest pageRequest);

    @Query(value = "select e from Event e where e.id = ?1")
    Event getReferenceById(long eventId);

    @Query(value = "select e from Event e where e.state = ?1 and e.category in (?2) and e.paid = ?3")
    Page<Event> getAllPublished(String published, List<Long> categories, boolean paid, PageRequest pageRequest);

    @Query(value = "select e from Event e join EventCompilation ec on e.id = ec.eventId " +
            "where ec.compilationId = ?1")
    List<Event> getCompilationsEvents(long compilationId);

    @Query(value = "select e from Event e where e.state in (?1) and e.category in (?2) and e.initiator in (?3)")
    Page<Event> getAllAdmin(List<String> state, List<Long> categories, List<Long> users, Pageable pageable);
}
