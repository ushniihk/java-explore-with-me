package ru.practicum.exploreWithMe.category.dto;


import ru.practicum.exploreWithMe.category.model.Category;

public class CategoryMapper {
    public static Category toCategory(CategoryDto dto) {
        return new Category(
                dto.getId(),
                dto.getName()
        );
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
