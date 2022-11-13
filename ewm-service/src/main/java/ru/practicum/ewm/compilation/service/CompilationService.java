package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto add(NewCompilationDto newCompilationDto);

    void delete(long compId);

    void deleteEvent(long compId, long eventId);

    void addEvent(long compId, long eventId);

    void pin(long compId);

    void unpin(long compId);

    CompilationDto get(long compId);

    List<CompilationDto> getAll(boolean pinned, int from, int size);
}
