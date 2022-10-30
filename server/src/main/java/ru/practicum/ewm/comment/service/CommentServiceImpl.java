package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.CommentMapper;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exceptions.IncorrectParameterException;
import ru.practicum.ewm.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto add(long userId, long eventId, NewCommentDto newCommentDto) {
        checkUserId(userId);
        checkEventId(eventId);
        return commentMapper.toCommentDto(
                commentRepository.save(commentMapper.toComment(newCommentDto, userId, eventId, LocalDateTime.now())));
    }

    @Override
    public void delete(long userId, long eventId, long commId) {
        checkUserId(userId);
        checkEventId(eventId);
        checkCommId(commId);
        Comment comment = commentRepository.getReferenceById(commId);
        if (comment.getUserId() != userId)
            throw new IncorrectParameterException("you can't delete someone else's comment");
        if (comment.getEventId() != eventId)
            throw new IncorrectParameterException("this comment from another event");
        commentRepository.deleteById(commId);
    }

    @Override
    public CommentDto update(long userId, long eventId, CommentDto commentDto) {
        checkUserId(userId);
        checkEventId(eventId);
        return null;
    }

    @Override
    public List<CommentDto> getAllByUser(long userId) {
        checkUserId(userId);
        commentRepository.getA
        return null;
    }

    @Override
    public List<CommentDto> getAllByEvent(long eventId) {
        checkEventId(eventId);
        return null;
    }

    @Override
    public CommentDto get(long eventId, long commId) {
        checkEventId(eventId);
        checkCommId(commId);
        return null;
    }

    @Override
    public void delete(long commId) {
        checkCommId(commId);
        commentRepository.deleteById(commId);
    }

    @Override
    public void deleteByAdmin(long commId) {

    }

    void checkCommId(long commId) {
        if (!commentRepository.existsById(commId))
            throw new IncorrectParameterException("bad commId");
    }

    void checkEventId(long eventId) {
        if (!eventRepository.existsById(eventId))
            throw new IncorrectParameterException("bad eventId");
    }

    void checkUserId(long userId) {
        if (!userRepository.existsById(userId))
            throw new IncorrectParameterException("bad userId");
    }
}
