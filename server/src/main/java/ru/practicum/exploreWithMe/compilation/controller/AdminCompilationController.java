package ru.practicum.exploreWithMe.compilation.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.compilation.service.CompilationService;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Data
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto add(@RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.add(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable long compId) {
        compilationService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.deleteEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.addEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void pin(@PathVariable long compId) {
        compilationService.pin(compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpin(@PathVariable long compId) {
        compilationService.unpin(compId);
    }

}
