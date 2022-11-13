package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDTO;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
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
    public EventFullDto add(@PathVariable long userId, @RequestBody NewEventDto eventDto) {
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

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDTO> getRequestsForEvent(@PathVariable long userId, @PathVariable long eventId) {
        return eventService.getRequestsForEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDTO confirm(@PathVariable long userId, @PathVariable long eventId,
                                           @PathVariable long reqId) {
        return eventService.confirm(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDTO reject(@PathVariable long userId, @PathVariable long eventId, @PathVariable long reqId) {
        return eventService.reject(userId, eventId, reqId);
    }

}
