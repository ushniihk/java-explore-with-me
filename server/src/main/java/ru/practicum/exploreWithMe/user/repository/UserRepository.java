package ru.practicum.exploreWithMe.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);

    @Query(value = "select u from User u where u.id in (?1)")
    Page<User> findAllByIds(List<Long> ids, PageRequest pageRequest);
}
