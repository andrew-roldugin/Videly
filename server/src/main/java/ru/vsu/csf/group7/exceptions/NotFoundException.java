package ru.vsu.csf.group7.exceptions;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("Ничего не найдено");
    }
}
