package ru.practicum.exploreWithMe.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.category.model.Category;
import ru.practicum.exploreWithMe.user.model.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    @Query(value = "select c from Category c")
    Page<Category> findAll(PageRequest pageRequest);
}
