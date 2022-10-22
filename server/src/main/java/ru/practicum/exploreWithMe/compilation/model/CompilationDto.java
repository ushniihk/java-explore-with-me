package ru.practicum.exploreWithMe.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.exploreWithMe.event.model.EventShortDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private long id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}
