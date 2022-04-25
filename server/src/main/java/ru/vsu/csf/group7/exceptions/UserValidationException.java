package ru.vsu.csf.group7.exceptions;

public class UserValidationException extends ApiException {
    public UserValidationException(String field) {
        super(String.format("Поле %s должно быть заполнено", field));
    }
}
