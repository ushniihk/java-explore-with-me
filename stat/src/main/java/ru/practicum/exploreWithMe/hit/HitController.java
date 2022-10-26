package ru.practicum.exploreWithMe.hit;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "")
@RequiredArgsConstructor
@Data
public class HitController {
    private final HitService hitService;

    @PostMapping("/hit")
    public void add(@RequestBody HitDto hitDto, HttpServletRequest request) {
        // eventClient.addHit(request);
        hitService.add(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatDto> get(@RequestParam String start,
                                 @RequestParam String end,
                                 @RequestParam List<String> uris,
                                 @RequestParam(required = false, defaultValue = "false") boolean unique) {
        return hitService.get(start, end, uris, unique);
    }
}
