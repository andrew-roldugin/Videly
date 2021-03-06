package ru.vsu.csf.group7.http.request;

import lombok.Data;
import ru.vsu.csf.group7.annotations.PasswordMatches;
import ru.vsu.csf.group7.entity.ERole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "Проверьте поле \"email\"")
    @NotBlank(message = "Поле email обязательно для заполнения")
    private String email;
//
//    @NotBlank(message = "Поле никнейм обязательно для заполнения")
//    private String nickname;

    @NotEmpty(message = "Поле \"пароль\" обязательно для заполнения")
    @Size(min = 6, max = 30)
    private String password;

    @NotEmpty(message = "Поле \"повторите пароль\" обязательно для заполнения")
    @Size(min = 6, max = 30)
    private String confirmPassword;

    private ERole role = ERole.USER;
}
