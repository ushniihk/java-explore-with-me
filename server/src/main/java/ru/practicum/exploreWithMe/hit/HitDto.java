package ru.practicum.exploreWithMe.hit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class HitDto {
    private String api;
    private String uri;
    private String ip;
    private String timestamp;
}
