package ru.vsu.csf.group7.http.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "Поле логин не может быть пустым")
    private String login;
    @NotEmpty(message = "Поле пароль не может быть пустым")
    private String password;
}
