package ru.practicum.ewm.category.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exceptions.CreatingException;
import ru.practicum.ewm.exceptions.NotFoundParameterException;
import ru.practicum.ewm.exceptions.UpdateException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        checkForCreate(categoryDto);
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(categoryDto)));
    }

    @Override
    public void delete(long catId) {
        if (!categoryRepository.existsById(catId))
            throw new UpdateException("sorry, but category with this id is not exist");
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        checkForUpdate(categoryDto);
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(categoryDto)));
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return categoryRepository.findAll(pageRequest).stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto get(long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new NotFoundParameterException("bad id");
        }
        return CategoryMapper.toCategoryDto(categoryRepository.getReferenceById(catId));
    }

    private void checkForUpdate(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.getName()))
            throw new UpdateException("sorry, but this name is already exist");
        if (!categoryRepository.existsById(dto.getId()))
            throw new UpdateException("sorry, but category with this id is not exist");
    }

    private void checkForCreate(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.getName()))
            throw new CreatingException("sorry, but this name is already exist");
    }

}
