package ru.practicum.ewm.comment.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
@Data
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("/{eventId}")
    public List<CommentDto> getAllByEvent(@PathVariable long eventId){
        return commentService.getAllByEvent(eventId);
    }

    @GetMapping("/{eventId}/{commId}")
    public CommentDto get(@PathVariable long eventId, @PathVariable long commId){
        return commentService.get(eventId, commId);
    }

}
