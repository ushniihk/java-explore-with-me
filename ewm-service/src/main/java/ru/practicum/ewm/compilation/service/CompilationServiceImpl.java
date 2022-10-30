package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationMapper;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.*;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.compilation.repository.EventCompilationRepository;
import ru.practicum.ewm.exceptions.IncorrectParameterException;
import ru.practicum.ewm.exceptions.UpdateException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventCompilationRepository eventCompilationRepository;

    @Override
    @Transactional
    public CompilationDto add(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto);
        compilation = compilationRepository.save(compilation);
        for (Long eventId : newCompilationDto.getEvents()) {
            EventCompilation eventCompilation = new EventCompilation(eventId, compilation.getId());
            eventCompilationRepository.save(eventCompilation);
        }
        return compilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void delete(long compId) {
        checkCompId(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEvent(long compId, long eventId) {
        checkCompId(compId);
        Optional<EventCompilation> ec = eventCompilationRepository.getByEventIdAndCompilationId(compId, eventId);
        if (ec.isEmpty())
            throw new IncorrectParameterException("there aren't this events id in this compilation");
        eventCompilationRepository.delete(ec.get());
    }

    @Override
    public void addEvent(long compId, long eventId) {
        checkCompId(compId);
        if (eventCompilationRepository.existsByCompilationIdAndEventId(compId, eventId))
            throw new IncorrectParameterException("this event has already been in this collection");
        eventCompilationRepository.save(new EventCompilation(eventId, compId));
    }

    @Override
    public void pin(long compId) {
        checkCompId(compId);
        Compilation compilation = compilationRepository.getReferenceById(compId);
        if (compilation.isPinned())
            throw new UpdateException("compilation has already been pinned");
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    public void unpin(long compId) {
        checkCompId(compId);
        Compilation compilation = compilationRepository.getReferenceById(compId);
        if (!compilation.isPinned())
            throw new UpdateException("compilation has already been unpinned");
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationDto get(long compId) {
        checkCompId(compId);
        return compilationMapper.toCompilationDto(compilationRepository.getReferenceById(compId));
    }

    @Override
    public List<CompilationDto> getAll(boolean pinned, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return compilationRepository.findAll(pinned, pageRequest).stream()
                .map(compilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    private void checkCompId(long compId) {
        if (!compilationRepository.existsById(compId))
            throw new IncorrectParameterException("bad compilation id");
    }
}
