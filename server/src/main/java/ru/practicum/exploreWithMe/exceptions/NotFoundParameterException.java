package ru.practicum.exploreWithMe.exceptions;

public class NotFoundParameterException extends RuntimeException {
    public NotFoundParameterException(String message) {
        super(message);
    }
}
