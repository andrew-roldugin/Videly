package ru.vsu.csf.group7.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.http.request.UpdateUserRequest;
import ru.vsu.csf.group7.http.response.AccountDetailsResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.http.response.MessageWithDataResponse;

import javax.validation.Valid;
import java.security.Principal;
@Tag(name = "UserController", description = "Модуль управления аккаунтами пользователей")
@SecurityRequirement(name = "bearer_token")
public interface IUserAPI {
    @Operation(
            summary = "Загрузка данных об аккаунте пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(schema = @Schema(implementation = AccountDetailsResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> myAccount(Principal principal);

    @Operation(
            summary = "Обновление пользовательских данных",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные обновлены", content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<MessageResponse> updateUser(
            @Parameter(description = "ID обновляемого пользователя", required = true) @PathVariable("userId") String userId,
            @Valid @RequestBody UpdateUserRequest req,
            BindingResult bindingResult
    );

    //    @PreAuthorize("#userId.equals(authentication.principal.id.toString()) or hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Удаление аккаунта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<MessageResponse> deleteUserAccount(@Parameter(description = "ID удаляемого аккаунта", required = true) @PathVariable("userId") String userId);
}
