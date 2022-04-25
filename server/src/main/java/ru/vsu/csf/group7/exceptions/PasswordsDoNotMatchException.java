package ru.vsu.csf.group7.exceptions;

public class PasswordsDoNotMatchException extends ApiException {
    public PasswordsDoNotMatchException() {
        super("Вы ввели неверный пароль");
    }

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
