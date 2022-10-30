package ru.practicum.ewm.comment.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto add(long userId, long eventId, NewCommentDto newCommentDto);

    void delete(long userId, long eventId, long commId);

    CommentDto update(long userId, long eventId, CommentDto commentDto);

    List<CommentDto> getAllByUser(long userId);

    List<CommentDto> getAllByEvent(long eventId);

    CommentDto get(long eventId, long commId);

    void delete(long commId);
    void deleteByAdmin(long commId);
}
