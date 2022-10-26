package ru.practicum.exploreWithMe.hit;

import java.util.List;

public interface HitService {
    void add(HitDto hitDto);

    List<ViewStatDto> get(String start, String end, List<String> uris, boolean unique);
}
