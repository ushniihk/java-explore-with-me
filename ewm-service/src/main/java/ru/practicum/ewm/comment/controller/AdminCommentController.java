package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;

    @DeleteMapping("/{commId}")
    public void delete(@PathVariable long commId) {
        commentService.deleteByAdmin(commId);
    }

    @GetMapping("")
    public List<CommentDto> getAll(@RequestParam(required = false, defaultValue = "0") int from,
                                   @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.getAll(from, size);
    }

    @GetMapping("")
    public List<CommentDto> findByText(@RequestParam String text,
                                   @RequestParam(required = false, defaultValue = "0") int from,
                                   @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.findByText(text, from, size);
    }
}
