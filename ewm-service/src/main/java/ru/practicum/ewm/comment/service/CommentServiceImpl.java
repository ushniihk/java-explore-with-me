package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.stream.Collectors;

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
    @Transactional
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
    @Transactional
    public CommentDto update(long userId, long eventId, CommentDto commentDto) {
        checkUserId(userId);
        checkEventId(eventId);
        if (commentDto.getUser().getId() != userId)
            throw new IncorrectParameterException("you can't update someone else's comment");
        if (commentDto.getEvent().getId() != eventId)
            throw new IncorrectParameterException("this comment from another event");
        return commentMapper.toCommentDto(
                commentRepository.save(commentMapper.toComment(commentDto)));
    }

    @Override
    public List<CommentDto> getAllByUser(long userId, int from, int size) {
        checkUserId(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("CREATED"));
        return commentRepository.getAllByUserId(userId, pageRequest).stream()
                .map(commentMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllByEvent(long eventId, int from, int size) {
        checkEventId(eventId);
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("CREATED"));
        return commentRepository.getAllByEventId(eventId, pageRequest).stream()
                .map(commentMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto get(long eventId, long commId) {
        checkEventId(eventId);
        checkCommId(commId);
        Comment comment = commentRepository.getReferenceById(commId);
        if (comment.getEventId() != eventId)
            throw new IncorrectParameterException("Event doesn't have this comment");
        return commentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteByAdmin(long commId) {
        checkCommId(commId);
        commentRepository.deleteById(commId);
    }

    @Override
    public List<CommentDto> getAll(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("CREATED"));
        return commentRepository.findAll(pageRequest).stream()
                .map(commentMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findByText(String text, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("CREATED"));
        return commentRepository.findAllByMessageContainsIgnoreCase(text, pageRequest).stream()
                .map(commentMapper::toCommentDto).collect(Collectors.toList());
    }

    private void checkCommId(long commId) {
        if (!commentRepository.existsById(commId))
            throw new IncorrectParameterException("bad commId");
    }

    private void checkEventId(long eventId) {
        if (!eventRepository.existsById(eventId))
            throw new IncorrectParameterException("bad eventId");
    }

    private void checkUserId(long userId) {
        if (!userRepository.existsById(userId))
            throw new IncorrectParameterException("bad userId");
    }
}
