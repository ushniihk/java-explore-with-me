package ru.practicum.exploreWithMe.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned = false;
    private String title;
}
