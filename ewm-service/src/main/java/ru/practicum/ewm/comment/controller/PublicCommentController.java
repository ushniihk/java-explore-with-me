package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/comments/{eventId}")
@RequiredArgsConstructor
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("")
    public List<CommentDto> getAllByEvent(@PathVariable long eventId,
                                          @RequestParam(required = false, defaultValue = "0") int from,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.getAllByEvent(eventId, from, size);
    }

    @GetMapping("/{commId}")
    public CommentDto get(@PathVariable long eventId, @PathVariable long commId) {
        return commentService.get(eventId, commId);
    }

}
