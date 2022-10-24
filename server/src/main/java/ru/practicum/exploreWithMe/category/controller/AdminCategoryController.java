package ru.practicum.exploreWithMe.category.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.category.service.CategoryService;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Data
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PatchMapping
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @PostMapping
    public CategoryDto add(@RequestBody CategoryDto categoryDto) {
        return categoryService.add(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable long catId) {
        categoryService.delete(catId);
    }
}
