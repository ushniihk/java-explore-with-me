package ru.practicum.exploreWithMe.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {

    private HttpStatus status;
    private String message;
    private String reason;
    private LocalDateTime timestamp;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors, String reason) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, String reason) {
        super();
        this.status = status;
        this.message = message;
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }
}
