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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.csf.group7.dto.RoomDTO;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.http.response.MessageWithDataResponse;

@Tag(name = "RoomController", description = "Модуль управления комнатами для совместного просмотра")
@SecurityRequirement(name = "bearer_token")
public interface IRoomAPI {
    @Operation(
            summary = "Создать комнату для совместного просмотра",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = RoomDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> create(
            @Parameter(description = "ID пользователя-автора комнаты", required = true) @RequestParam("userId") String userId,
            BindingResult bindingResult
    );

    @Operation(
            summary = "Закрыть комнату",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Комната не найдена", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> close(
            @Parameter(description = "ID комнаты", required = true) @PathVariable("roomId") String roomId,
            BindingResult bindingResult
    );
}
