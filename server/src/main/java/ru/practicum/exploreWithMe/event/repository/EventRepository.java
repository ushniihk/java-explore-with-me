package ru.practicum.exploreWithMe.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> getAllByInitiator(long userId, PageRequest pageRequest);
    @Query(value = "select e from Event e where e.paid = ?3 and e.category in ?2 and e.state = ?1")
    Page<Event> getAllPublished(String published, String[] categories, boolean paid, PageRequest pageRequest);

    @Query(value = "select e " +
            "from Event e join EventCompilation ec on e.id = ec.eventId " +
            "where ec.compilationId = ?1")
    List<Event> getCompilationsEvents(long compilationId);

}
