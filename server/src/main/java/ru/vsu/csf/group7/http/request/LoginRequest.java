package ru.vsu.csf.group7.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Поле логин не может быть пустым")
    private String email;
    @NotEmpty(message = "Поле пароль не может быть пустым")
    private String password;
}
