package ru.practicum.exploreWithMe.participationRequest;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.model.NewEventDto;
import ru.practicum.exploreWithMe.participationRequest.service.ParticipationRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Data
public class PrivateRequestController {

    ParticipationRequestService service;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDTO> get(@PathVariable long userId) {
        return service.getAllByRequestor(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDTO add(@PathVariable long userId, @RequestParam long eventId) {
        return service.add(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{requestId}/cancel")
    public void cancel(@PathVariable long userId, @PathVariable long requestId) {
        service.cancel(userId, requestId);
    }

}
