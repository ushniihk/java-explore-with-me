package ru.practicum.exploreWithMe.compilation.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.compilation.model.CompilationDto;
import ru.practicum.exploreWithMe.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Data
public class PublicCompilationController {

    CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(required = false) boolean pinned,
                                       @RequestParam(required = false, defaultValue = "0") int from,
                                       @RequestParam(required = false, defaultValue = "10") int size) {
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto get(@PathVariable long compId) {
        return compilationService.get(compId);
    }
}
