package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getAll(@RequestParam List<Long> users,
                                    @RequestParam List<String> states,
                                    @RequestParam List<Long> categories,
                                    @RequestParam String rangeStart,
                                    @RequestParam String rangeEnd,
                                    @RequestParam (required = false, defaultValue = "0") int from,
                                    @RequestParam (required = false, defaultValue = "10") int size) {
        return eventService.getAllByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto update(@PathVariable long eventId, @RequestBody UpdateEventRequest eventDto) {
        return eventService.adminUpdate(eventId, eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publish(@PathVariable long eventId) {
        return eventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto reject(@PathVariable long eventId) {
        return eventService.adminReject(eventId);
    }
}
