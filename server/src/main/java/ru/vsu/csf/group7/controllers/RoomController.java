package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.RoomDTO;
import ru.vsu.csf.group7.dto.SubscriptionDTO;
import ru.vsu.csf.group7.entity.Room;
import ru.vsu.csf.group7.http.response.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
@AllArgsConstructor
@Log4j2
@Tag(name = "RoomController", description = "Модуль управления комнатами для совм. просмотра")
public class RoomController {

    @GetMapping(value = "/",  produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Создать комнату для совместного просмотра",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = RoomDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<Object> create(
            @Parameter(description = "ID пользователя-автора комнаты", required = true) @RequestParam("userId") String userId,
            BindingResult bindingResult
    ) {

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{roomId}",  produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Закрыть комнату",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Комната не найдена", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<Object> close(
            @Parameter(description = "ID комнаты", required = true) @PathVariable("roomId") String roomId,
            BindingResult bindingResult
    ) {

        return ResponseEntity.noContent().build();
    }
}
