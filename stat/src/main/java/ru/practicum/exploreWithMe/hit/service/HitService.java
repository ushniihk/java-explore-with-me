package ru.practicum.exploreWithMe.hit.service;

import ru.practicum.exploreWithMe.hit.dto.HitDto;
import ru.practicum.exploreWithMe.hit.dto.ViewStatDto;

import java.util.List;

public interface HitService {
    void add(HitDto hitDto);

    List<ViewStatDto> get(String start, String end, List<String> uris, boolean unique);
}
