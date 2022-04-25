package ru.vsu.csf.group7.exceptions;

public class NotAllowedExceptions extends ApiException {
    public NotAllowedExceptions(Long userId, Long targetId, String action) {
        super(String.format("Пользователю с id=%s запрещено %s %s", userId, action, targetId));
    }

    public NotAllowedExceptions(Long userId, String action) {
        super(String.format("Пользователю с id=%s запрещено %s", userId, action));
    }

    public NotAllowedExceptions() {
        super("Вам запрещено выполнять это действие");
    }
}
