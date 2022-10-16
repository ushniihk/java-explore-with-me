package ru.practicum.exploreWithMe.event.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.exploreWithMe.event.model.NewEventDto;
import ru.practicum.exploreWithMe.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Data
public class PublicEventController {

    EventService eventService;

    @GetMapping
    public List<NewEventDto> getAll(){
        return null;
    }

    @GetMapping("/{eventId}")
    public NewEventDto get(@PathVariable long eventId){
        return null;
    }
}
