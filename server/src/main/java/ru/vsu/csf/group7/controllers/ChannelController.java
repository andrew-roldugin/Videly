package ru.vsu.csf.group7.controllers;

import com.fasterxml.jackson.databind.util.ObjectBuffer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.*;
import ru.vsu.csf.group7.http.response.JWTTokenSuccessResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.VideoService;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("/api/channel")
@AllArgsConstructor
@Log4j2
@Tag(name = "ChannelController", description = "Модуль обработки каналов")
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping(value = "/createNew", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Создание нового канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Новый канал успешно создан", content = @Content(schema = @Schema(implementation = ChannelDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Произошла неизвестная ошибка при создании канала", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<Object> createNewChannel(@Valid @RequestBody CreateChannelRequest request
//                                                   @Parameter(name = "avatarImg", description = "Файл-аватар канала") MultipartFile avatarImg,
//                                                   @Parameter(name = "headerImg", description = "Файл-шапка канала") MultipartFile headerImg
    ) {
        Channel channel = null;
        try {
            channel = channelService.create(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при создании канала");
        }
        return ResponseEntity.ok().body(ChannelDTO.fromChannel(channel));
    }

    @GetMapping(value = "/{channelId}", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Загрузка данных о канале",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(schema = @Schema(implementation = ChannelDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Канал не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Загрузка данных, когда пользователь переходит на какой-либо канал"
    )
    public ResponseEntity<Object> load(@Parameter(description = "ID кананла", required = true)  @PathVariable("channelId") String channelId) {
        try {
            return ResponseEntity.ok(ChannelDTO.fromChannel(channelService.findById(channelId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Ошибка при загрузке данныз канала " + e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла внутрення ошибка сервера"));
        }
    }

    @GetMapping(value = "/find", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Поиск канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Есть данные по запросу", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<List<ChannelDTO>> findByQuery(@Parameter(description = "Поисковый запрос") @Valid @RequestBody SearchChannelQuery query) {
        return ResponseEntity.ok(channelService.findChannels(query).stream().map(ChannelDTO::fromChannel).toList());
    }

    @PatchMapping(value = "/update/{channelId}", produces = {"application/json", "multipart/form-data"}, consumes = "application/json")
    @Operation(
            summary = "Обновление данных канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные о канале успешно обновлены", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Канал не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> update(
            @Parameter(description = "ID обновляемого кананла", required = true) @PathVariable("channelId") String channelId,
            @RequestBody UpdateChannelRequest req
//            @RequestParam("avatarImg") @Parameter(name = "avatarImg", description = "Файл-аватар канала") MultipartFile avatarImg,
//            @RequestParam("headerImg") @Parameter(name = "headerImg", description = "Файл-шапка канала") MultipartFile headerImg
    ) {
        Channel c = channelService.updateById(req, channelId);
        return ResponseEntity.ok(new MessageResponse("Данные о канале обновлены", ChannelDTO.fromChannel(c)));
    }

    @DeleteMapping(value = "/delete/{channelId}", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Удаление канала",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Канал успешно удален", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Канал не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> delete(@Parameter(description = "ID удаляемого канала", required = true) @PathVariable("channelId") String channelId) {
        channelService.deleteById(channelId);
        return ResponseEntity.ok(new MessageResponse("Канал успешно удален"));
    }
}
