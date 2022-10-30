package ru.practicum.ewm.participationRequest.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.participationRequest.dto.ParticipationRequestDTO;
import ru.practicum.ewm.participationRequest.service.ParticipationRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Data
public class PrivateRequestController {

    private final ParticipationRequestService service;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDTO> get(@PathVariable long userId) {
        return service.getAllByRequestor(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDTO add(@PathVariable long userId, @RequestParam long eventId) {
        return service.add(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDTO cancel(@PathVariable long userId, @PathVariable long requestId) {
        return service.cancel(userId, requestId);
    }

}
