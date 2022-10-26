package ru.practicum.exploreWithMe.hit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStatDto {
    private String app;
    private String uri;
    private Long hits;
}
