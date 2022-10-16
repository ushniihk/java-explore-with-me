package ru.practicum.exploreWithMe.event.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.model.NewEventDto;
import ru.practicum.exploreWithMe.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Data
public class AdminEventController {

    EventService eventService;

    @GetMapping
    public List<NewEventDto> getAll() {
        return null;
    }

    @PutMapping("/{eventId}")
    public NewEventDto update(@PathVariable long eventId, @RequestBody NewEventDto eventDto) {
        return null;
    }

    @PatchMapping("/{eventId}/publish")
    public List<NewEventDto> publish(@PathVariable long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/reject")
    public List<NewEventDto> reject(@PathVariable long eventId) {
        return null;
    }
}
