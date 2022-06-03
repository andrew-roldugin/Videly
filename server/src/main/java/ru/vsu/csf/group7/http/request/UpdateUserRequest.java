package ru.vsu.csf.group7.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Запрос на обновление пользовательских данных")
public class UpdateUserRequest {
    @Schema(description = "Новый пароль")
    @NotEmpty
    @Size(min = 6)
    private String password;
    //private boolean isBanned = false;
}
