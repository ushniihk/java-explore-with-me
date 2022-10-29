package ru.practicum.ewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;

import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Component
public class CompilationMapper {
    private final CompilationRepository compilationRepository;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public Compilation toCompilation(NewCompilationDto compilationDto) {
        return new Compilation(
                compilationDto.isPinned(),
                compilationDto.getTitle()
        );
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                eventRepository.getCompilationsEvents(compilation.getId()).stream()
                        .map(eventMapper::toEventShortDto).collect(Collectors.toList()),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }
}
