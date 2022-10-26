package ru.practicum.exploreWithMe.hit;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "")
@RequiredArgsConstructor
@Data
public class HitController {
    private final HitService hitService;
    @PostMapping("/hit")
    public String add(@RequestBody HitDto hitDto){
        return "SEX";
      //  hitService.add(hitDto);
    }
    @GetMapping("/stats")
    public Stats get(@RequestParam String start,
                     @RequestParam String end,
                     @RequestParam(required = false) String[] uris,
                     @RequestParam(required = false, defaultValue = "false") boolean unique ){
        return hitService.get(start, end, uris, unique);
    }
}
