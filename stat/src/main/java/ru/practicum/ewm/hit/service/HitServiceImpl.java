package ru.practicum.ewm.hit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.hit.model.Hit;
import ru.practicum.ewm.hit.repository.HitRepository;
import ru.practicum.ewm.hit.dto.HitDto;
import ru.practicum.ewm.hit.dto.HitMapper;
import ru.practicum.ewm.hit.dto.ViewStatDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Override
    @Transactional
    public void add(HitDto hitDto) {
        hitRepository.save(hitMapper.toHit(hitDto));
    }

    @Override
    public List<ViewStatDto> get(String start, String end, List<String> uris, boolean unique) {
        List<Hit> views;
        if (uris != null) {
            views = hitRepository.getHitsByTimestampAfterAndTimestampBeforeAndUriIn(LocalDateTime.parse(start, dtf), LocalDateTime.parse(end, dtf), uris);
        } else {
            views = hitRepository.getHitsByTimestampAfterAndTimestampBefore(LocalDateTime.parse(start, dtf), LocalDateTime.parse(end, dtf));
        }

        if (unique)
            views = makeUniqueList(views);

        return views.stream().map(hitMapper::viewStatDto).collect(Collectors.toList());
    }

    private List<Hit> makeUniqueList(List<Hit> hits) {
        Map<String, Hit> uniqueHits = new HashMap<>();
        hits.forEach(hit -> uniqueHits.put(hit.getIp(), hit));
        return new ArrayList<>(uniqueHits.values());
    }
}
