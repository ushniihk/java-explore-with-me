package ru.practicum.exploreWithMe.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.compilation.model.Compilation;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.user.model.User;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query(value = "select c from Compilation c where c.pinned = ?1")
    Page<Compilation> findAll(boolean pinned, PageRequest pageRequest);

}
