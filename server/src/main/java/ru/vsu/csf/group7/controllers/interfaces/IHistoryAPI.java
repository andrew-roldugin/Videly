package ru.vsu.csf.group7.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.http.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.csf.group7.dto.HistoryDTO;
import ru.vsu.csf.group7.http.response.MessageResponse;

@Tag(name = "HistoryController", description = "Модуль работы с историей просмотров")
@SecurityRequirement(name = "bearer_token")
public interface IHistoryAPI {
    @Operation(
            summary = "Загрузка истории просмотров пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HistoryDTO.class)))),
                    @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Загрузка истории просмотров конкреного пользоавтеля"
    )
    ResponseEntity<Object> getViewsHistory(
            @Parameter(description = "Ограничение для выборки") @RequestParam(value = "limit") int limit,
            @Parameter(description = "Смещение от начального элемента") @RequestParam(value = "offset") int offset
    );

    @Operation(
            summary = "Добавить новое видео в историю просмотров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = HistoryDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
//                    @ApiResponse(responseCode = "403", description = "Действие запрещено", content = @Content(schema = @Schema(implementation = HttpException.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> addToHistory(@Parameter(name = "videoId", description = "ID просмотренного видео", required = true) String videoId);
}
