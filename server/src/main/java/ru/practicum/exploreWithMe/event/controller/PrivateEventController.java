package ru.practicum.exploreWithMe.event.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.model.EventFullDto;
import ru.practicum.exploreWithMe.event.model.EventShortDto;
import ru.practicum.exploreWithMe.event.model.NewEventDto;
import ru.practicum.exploreWithMe.event.model.UpdateEventRequest;
import ru.practicum.exploreWithMe.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping(path = "/private/users")
@RequiredArgsConstructor
@Data
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto get(@PathVariable long userId, @PathVariable long eventId) {
        return eventService.get(userId, eventId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getAll(@PathVariable long userId, @RequestParam(required = false, defaultValue = "0") int from,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return eventService.getAllByUser(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    public NewEventDto add(@PathVariable long userId, @RequestBody NewEventDto eventDto) {
        return eventService.add(userId, eventDto);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancel(@PathVariable long userId, @PathVariable long eventId) {
        return eventService.cancel(userId, eventId);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto update(@PathVariable long userId, @RequestBody UpdateEventRequest eventDto) {
        return eventService.update(userId, eventDto);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public NewEventDto confirm(@PathVariable long userId, @PathVariable long eventId, @PathVariable long reqId) {
        return eventService.confirm(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public NewEventDto reject(@PathVariable long userId, @PathVariable long eventId, @PathVariable long reqId) {
        return eventService.reject(userId, eventId, reqId);
    }

}
