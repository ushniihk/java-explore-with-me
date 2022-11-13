package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.hit.HitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventClient extends BaseClient {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public EventClient(RestTemplateBuilder builder) {
        super(builder.build());
    }

    public ResponseEntity<Object> addHit(HttpServletRequest request) {
        HitDto hitDto = new HitDto("ewm-service", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(dtf));
        return post("", hitDto);
    }

}
