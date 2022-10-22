package ru.practicum.exploreWithMe.hit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService{
    @Override
    public void add(HitDto hitDto) {

    }

    @Override
    public Stats get(String start, String end, String[] uris, boolean unique) {
        return null;
    }
}
