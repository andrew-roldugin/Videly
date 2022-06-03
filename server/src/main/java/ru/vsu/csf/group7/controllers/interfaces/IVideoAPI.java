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
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;
import ru.vsu.csf.group7.http.request.SearchVideoQuery;
import ru.vsu.csf.group7.http.request.UpdateVideoRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;

@Tag(name = "VideoController", description = "Модуль обработки видео")
@SecurityRequirement(name = "bearer_token")
public interface IVideoAPI {
    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "Видео успешно загружено", content = @Content(schema = @Schema(implementation = VideoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            },
            summary = "Загрузка нового видео в хранилище"
    )
    ResponseEntity<Object> uploadNewVideo(
            @Parameter(description = "Параметры для загрузки нового видео", required = true) @RequestBody CreateVideoRequest request
//            @Parameter(required = true, description = "Загружаемый видеофайл") @RequestParam("video") MultipartFile video,
//            @RequestParam("preview") MultipartFile preview
    );

    @Operation(
            summary = "Загрузка данных о видео",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(schema = @Schema(implementation = VideoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Видео не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Загрузка данных, когда пользователь открывает какое-либо видео\n" +
                    "А также обновление количества просмотров"
    )
    ResponseEntity<Object> loadVideo(@Parameter(description = "ID загружаемого видео", required = true) @PathVariable("videoId") String videoId);

    @Operation(
            summary = "Поиск видео по различным критериям",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Есть данные по запросу", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VideoDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> findByQuery(@RequestBody SearchVideoQuery searchVideoQuery);

    @Operation(
            summary = "Обновление данных о видео",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные о видео успешно обновлены", content = @Content(schema = @Schema(implementation = VideoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Видео не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> update(@Parameter(description = "ID обновляемого видео", required = true) @PathVariable("videoId") String videoId, @RequestBody UpdateVideoRequest req);

    @Operation(
            summary = "Удаление видео",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Видео успешно удалено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Видео не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<MessageResponse> delete(
            @Parameter(description = "ID удаляемого видео", required = true) @PathVariable("videoId") String videoId,
            @Parameter(description = "Полностью удалить данные о видео?") @RequestParam(value = "fullDelete", required = false, defaultValue = "false") boolean fullDelete
    );
}
