package ru.practicum.exploreWithMe.category.service;

import ru.practicum.exploreWithMe.category.model.CategoryDto;
import ru.practicum.exploreWithMe.user.model.UserDto;

import java.util.List;

public interface CategoryService {

    CategoryDto add(CategoryDto categoryDto);

    void delete(long catId);

    CategoryDto update(CategoryDto categoryDto);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto get(long userId);
}
