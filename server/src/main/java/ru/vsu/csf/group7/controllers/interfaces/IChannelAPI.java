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
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;

import javax.validation.Valid;

@Tag(name = "ChannelController", description = "Модуль обработки каналов")

public interface IChannelAPI {
    @SecurityRequirement(name = "bearer_token")
    @Operation(
            summary = "Создание нового канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Новый канал успешно создан", content = @Content(schema = @Schema(implementation = ChannelDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Произошла неизвестная ошибка при создании канала", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> createNewChannel(@Valid @RequestBody CreateChannelRequest request
//                                                   @Parameter(name = "avatarImg", description = "Файл-аватар канала") MultipartFile avatarImg,
//                                                   @Parameter(name = "headerImg", description = "Файл-шапка канала") MultipartFile headerImg
    );
    @Operation(
            summary = "Загрузка данных о канале",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(schema = @Schema(implementation = ChannelDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Канал не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Загрузка данных, когда пользователь переходит на какой-либо канал"
    )
    ResponseEntity<Object> getBy(@Parameter(description = "ID канала") @PathVariable("channelId") String channelId,
                                 @Parameter(description = "ID пользователя", required = true) @PathVariable("userId") String userId
    );
    @Operation(
            summary = "Поиск канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Есть данные по запросу", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChannelDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> findByQuery(@Parameter(description = "Поисковый запрос") @Valid @RequestBody SearchChannelQuery query);

    @SecurityRequirement(name = "bearer_token")
    @Operation(
            summary = "Обновление данных канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные о канале успешно обновлены", content = @Content(schema = @Schema(implementation = ChannelDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Канал не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> update(
            @Parameter(description = "ID обновляемого кананла", required = true) @PathVariable("channelId") String channelId,
            @RequestBody UpdateChannelRequest req
//            @RequestParam("avatarImg") @Parameter(name = "avatarImg", description = "Файл-аватар канала") MultipartFile avatarImg,
//            @RequestParam("headerImg") @Parameter(name = "headerImg", description = "Файл-шапка канала") MultipartFile headerImg
    );

    @SecurityRequirement(name = "bearer_token")
    @Operation(
            summary = "Удаление канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Канал успешно удален", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Канал не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<MessageResponse> delete(
            @Parameter(description = "ID удаляемого канала", required = true) @PathVariable("channelId") String channelId,
            @Parameter(description = "Полностью удалить данные о канале?") @RequestParam(value = "fullDelete", required = false, defaultValue = "false") boolean fullDelete
    );

    @Operation(
            summary = "Загрузить все каналы",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChannelDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    ResponseEntity<Object> getAll();

}
