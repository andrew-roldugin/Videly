package ru.vsu.csf.group7.exceptions;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String login) {
        super(String.format("Пользователь с логином %s уже существует", login));
    }
}
