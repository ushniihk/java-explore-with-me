package ru.practicum.ewm.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class CommentDto {
    @Size(max = 300)
    private String message;
    private EventShortDto event;
    private UserShortDto user;
    private LocalDateTime created;
}
