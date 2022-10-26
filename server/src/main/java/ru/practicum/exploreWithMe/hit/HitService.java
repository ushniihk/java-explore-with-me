package ru.practicum.exploreWithMe.hit;

public interface HitService {
    void add(HitDto hitDto);
    Stats get(String start, String end, String[]uris, boolean unique);
}
