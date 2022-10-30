package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class PrivateCommentController {
    private final CommentService commentService;

    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto add(@PathVariable long userId, @PathVariable long eventId, @RequestBody NewCommentDto newCommentDto) {
        return commentService.add(userId, eventId, newCommentDto);
    }

    @DeleteMapping("/{userId}/events/{eventId}/comments/{commId}")
    public void delete(@PathVariable long userId, @PathVariable long eventId, @PathVariable long commId) {
        commentService.delete(userId, eventId, commId);
    }

    @PatchMapping("/{userId}/events/{eventId}/comments")
    public CommentDto update(@PathVariable long userId, @PathVariable long eventId, @RequestBody CommentDto commentDto) {
        return commentService.update(userId, eventId, commentDto);
    }

    @GetMapping("/{userId}/comments")
    public List<CommentDto> getAllByUser(@PathVariable long userId,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.getAllByUser(userId, from, size);
    }
}
