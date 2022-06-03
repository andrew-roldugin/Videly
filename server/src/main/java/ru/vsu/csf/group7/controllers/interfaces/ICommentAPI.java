package ru.vsu.csf.group7.controllers.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.csf.group7.dto.CommentDTO;
import ru.vsu.csf.group7.http.request.*;
import ru.vsu.csf.group7.http.response.MessageResponse;

import javax.validation.Valid;

public interface ICommentAPI {
    @Operation(
            summary = "Написать новый комментарий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = CommentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Переданы некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
                    @ApiResponse(responseCode = "403", description = "Действие запрещено"),
                    @ApiResponse(responseCode = "404", description = "Видео не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Произошла неизвестная ошибка", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> writeNewComment(@Valid @RequestBody CreateCommentRequest request);

    @Operation(
            summary = "Загрузить все комментарии под видео",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Данные не найдены", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> getAllComments(
            @Parameter(description = "ID видео", required = true) @RequestParam("videoId") String videoId,
            @RequestParam(value = "limit", defaultValue = "30") int limit,
            @RequestParam(value = "offset",defaultValue = "0") int offset
    );


    @Operation(
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = CommentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
                    @ApiResponse(responseCode = "403", description = "Действие запрещено"),
                    @ApiResponse(responseCode = "404", description = "Данные не найдены", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> update(@Valid @RequestBody UpdateCommentRequest request);


    @Operation(
            summary = "Удалить комментарий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
                    @ApiResponse(responseCode = "403", description = "Действие запрещено"),
                    @ApiResponse(responseCode = "404", description = "Данные не найдены", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<MessageResponse> delete(
            @Parameter(description = "ID удаляемого комментария", required = true) @RequestParam("commentId") String commentId,
            @Parameter(description = "ID видео", required = true) @RequestParam("videoId") String videoId,
            @Parameter(description = "Полностью удалить данные о комментарии?") @RequestParam(value = "fullDelete", required = false, defaultValue = "false") boolean fullDelete
    );

}
