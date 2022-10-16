package ru.practicum.exploreWithMe.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);

    @Query(value = "select u from User u")
    Page<User> findAll(PageRequest pageRequest);
}
