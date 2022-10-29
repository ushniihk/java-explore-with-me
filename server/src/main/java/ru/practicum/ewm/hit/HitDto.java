package ru.practicum.ewm.hit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitDto {
    private String api;
    private String uri;
    private String ip;
    private String timestamp;
}
