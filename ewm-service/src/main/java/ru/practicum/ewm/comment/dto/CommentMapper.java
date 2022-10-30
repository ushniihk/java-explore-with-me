package ru.practicum.ewm.comment.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CommentMapper {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getMessage(),
                eventMapper.toEventShortDto(eventRepository.getReferenceById(comment.getEventId())),
                UserMapper.toUserShortDto(userRepository.getReferenceById(comment.getUserId())),
                comment.getCreated()
        );
    }

    public Comment toComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getMessage(),
                commentDto.getEvent().getId(),
                commentDto.getUser().getId(),
                commentDto.getCreated()
        );
    }

    public Comment toComment(NewCommentDto newCommentDto, long userId, long eventId, LocalDateTime created) {
        return new Comment(
                newCommentDto.getMessage(),
                eventId,
                userId,
                created
        );
    }

}
