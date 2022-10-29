package ru.practicum.ewm.hit.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.hit.dto.HitDto;
import ru.practicum.ewm.hit.dto.ViewStatDto;
import ru.practicum.ewm.hit.service.HitService;

import java.util.List;

@RestController
@RequestMapping(path = "")
@RequiredArgsConstructor
@Data
public class HitController {
    private final HitService hitService;

    @PostMapping("/hit")
    public void add(@RequestBody HitDto hitDto) {
        hitService.add(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatDto> get(@RequestParam String start,
                                 @RequestParam String end,
                                 @RequestParam List<String> uris,
                                 @RequestParam(required = false, defaultValue = "false") boolean unique) {
        return hitService.get(start, end, uris, unique);
    }
}
