package ru.vsu.csf.group7.exceptions;

public class UserNotAuthorizedException extends ApiException {
    public UserNotAuthorizedException() {
        super("Пользователь не авторизован");
    }

    public UserNotAuthorizedException(String message) {
        super(message);
    }

}
