package ru.vsu.csf.group7.exceptions;

public class LoginAlreadyInUseException extends ApiException {
    public LoginAlreadyInUseException(String login) {
        super(String.format("Логин %s уже занят", login));
    }
}
