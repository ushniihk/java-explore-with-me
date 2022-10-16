package ru.practicum.exploreWithMe.compilation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.exploreWithMe.compilation.service.CompilationDto;
import ru.practicum.exploreWithMe.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Data
public class PublicCompilationController {

    CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll() {
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto get(@PathVariable long compId) {
        return null;
    }
}
