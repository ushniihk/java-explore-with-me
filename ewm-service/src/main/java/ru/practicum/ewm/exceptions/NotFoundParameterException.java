package ru.practicum.ewm.exceptions;

public class NotFoundParameterException extends RuntimeException {
    public NotFoundParameterException(String message) {
        super(message);
    }
}
