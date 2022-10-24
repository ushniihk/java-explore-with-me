package ru.practicum.exploreWithMe.participationRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDTO {
    private long id;
    private LocalDateTime created;
    private long event;
    private long requester;
    private String status;
}
