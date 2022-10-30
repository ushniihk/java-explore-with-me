package ru.practicum.ewm.participationRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParticipationRequestDTO {
    private long id;
    private LocalDateTime created;
    private long event;
    private long requester;
    private String status;
}
