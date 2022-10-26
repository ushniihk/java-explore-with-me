package ru.practicum.exploreWithMe.hit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Data
@Component
public class HitMapper {
    private final HitRepository hitRepository;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Hit toHit(HitDto hitDto) {
        return new Hit(
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                LocalDateTime.parse(hitDto.getTimestamp(), dtf)
        );
    }

    public ViewStatDto viewStatDto(Hit hit) {
        return new ViewStatDto(
                hit.getApp(),
                hit.getUri(),
                hitRepository.getContHit(hit.getUri())
        );
    }
}