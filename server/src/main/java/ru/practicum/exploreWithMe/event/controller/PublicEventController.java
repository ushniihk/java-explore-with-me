package ru.practicum.exploreWithMe.event.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Data
public class PublicEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getAll(@RequestParam String text, @RequestParam List<Long>categories,
                                    @RequestParam boolean paid, @RequestParam String rangeStart,
                                    @RequestParam String rangeEnd, @RequestParam String sort,
                                    @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                    @RequestParam(required = false, defaultValue = "0") int from,
                                    @RequestParam(required = false, defaultValue = "10") int size){
        return eventService.getAllPublished(text, categories, paid, rangeStart, rangeEnd, sort, onlyAvailable, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto get(@PathVariable long eventId){
        return eventService.getPublished(eventId);
    }
}
