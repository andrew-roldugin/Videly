package ru.vsu.csf.group7.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vsu.csf.group7.http.request.LoginRequest;
import ru.vsu.csf.group7.http.request.SignupRequest;
import ru.vsu.csf.group7.http.response.JWTTokenResponse;
import ru.vsu.csf.group7.http.response.JWTTokenSuccessResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.http.response.MessageWithDataResponse;

import javax.validation.Valid;

@Tag(name = "AuthController", description = "Аутентификация")
public interface IAuthAPI {
    @Operation(
            summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Авторизация прошла успешно", content = @Content(schema = @Schema(implementation = JWTTokenSuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Произошла ошибка при авторизации", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Произошла неизвестная ошибка при авторизации", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Процесс авторизации пользователя и получение для него пары токенов"
    )
    ResponseEntity<Object> signIn(@RequestBody LoginRequest loginRequest, BindingResult bindingResult);

    @Operation(
            summary = "Регистрация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Регистрация прошла успешно", content = @Content(schema = @Schema(implementation = JWTTokenResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Произошла ошибка при регистрации", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Произошла неизвестная ошибка при регистрации", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Процесс регистрации пользователя"
    )
    ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult);
}
