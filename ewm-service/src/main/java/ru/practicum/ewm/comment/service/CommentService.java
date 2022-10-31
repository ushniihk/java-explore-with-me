package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto add(long userId, long eventId, NewCommentDto newCommentDto);

    void delete(long userId, long eventId, long commId);

    CommentDto update(long userId, long eventId, CommentDto commentDto);

    List<CommentDto> getAllByUser(long userId, int from, int size);

    List<CommentDto> getAllByEvent(long eventId, int from, int size);

    CommentDto get(long eventId, long commId);

    void deleteByAdmin(long commId);

    List<CommentDto> getAll(int from, int size);

    List<CommentDto> findByText(String text, int from, int size);


}
