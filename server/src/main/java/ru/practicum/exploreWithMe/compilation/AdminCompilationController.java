package ru.practicum.exploreWithMe.compilation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.compilation.service.CompilationDto;
import ru.practicum.exploreWithMe.compilation.service.CompilationService;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Data
public class AdminCompilationController {
    CompilationService compilationService;

    @PostMapping
    public CompilationDto add(@RequestBody CompilationDto compilationDto) {
        return null;
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable long compId) {
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable long compId, @PathVariable long eventId) {
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable long compId, @PathVariable long eventId) {
    }

    @PatchMapping("/{compId}")
    public void pin(@PathVariable long compId) {
    }

}
