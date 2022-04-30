package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.HistoryDTO;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.History;
import ru.vsu.csf.group7.http.response.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin
@Tag(name = "HistoryController", description = "Модуль работы с историей просмотров")
public class HistoryController {

    @GetMapping(value = "/history/{userId}", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Загрузка истории просмотров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(schema = @Schema(implementation = HistoryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Загрузка истории просмотров конкреного пользоавтеля"
    )
    public ResponseEntity<List<VideoDTO>> getViewsHistory(@Parameter(description = "ID пользователя", required = true) @PathVariable("userId") String userId) {

        return null;
    }

    @PostMapping(value = "/history/{userId}", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Добавить новое видео в историю просмотров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = HistoryDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<?> addToHistory(
            @Parameter(description = "ID пользователя", required = true) @PathVariable("userId") String userId,
            @Parameter(description = "ID видео", required = true) String videoId) {

        return null;
    }
}
