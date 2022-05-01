package ru.vsu.csf.group7.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.csf.group7.dto.SubscriptionDTO;
import ru.vsu.csf.group7.http.response.AccountDetailsResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.http.response.MessageWithDataResponse;

@Tag(name = "SubscriptionsController", description = "Модуль управления платными подписками на каналы")
@SecurityRequirement(name = "bearer_token")
public interface ISubscriptionsAPI {
    @Operation(
            summary = "Загрузить подписки пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные получены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SubscriptionDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> getUserSubscriptions(
            @Parameter(description = "ID пользователя", required = true) @PathVariable("userId") String userId,
            BindingResult bindingResult
    );

    @Operation(
            summary = "Подписаться на канал",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> subscribe(
            @Parameter(description = "ID пользователя", required = true) @PathVariable("userId") String userId,
            @Parameter(description = "ID канала", required = true) @RequestParam("channelId") String channelId,
            BindingResult bindingResult
    );
}
