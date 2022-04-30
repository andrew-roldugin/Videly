package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.SubscriptionDTO;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.entity.Subscription;
import ru.vsu.csf.group7.http.request.UpdateUserRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin
@AllArgsConstructor
@Log4j2
@Tag(name = "SubscriptionsController", description = "Модуль управления платными подписками на каналы")
public class SubscriptionsController {

    @GetMapping(value = "/{userId}",  produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Загрузить подписки пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные получены", content = @Content(schema = @Schema(implementation = SubscriptionDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<Object> getUserSubscriptions(
            @Parameter(description = "ID пользователя", required = true) @PathVariable("userId") String userId,
            BindingResult bindingResult
    ) {

        return ResponseEntity.ok(List.of(new SubscriptionDTO()));
    }

    @PostMapping(value = "/{userId}",  produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Подписаться на канал",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<Object> subscribe(
            @Parameter(description = "ID пользователя", required = true) @PathVariable("userId") String userId,
            @Parameter(description = "ID канала", required = true) @PathVariable("channelId") String channelId,
            BindingResult bindingResult
    ) {

        return ResponseEntity.ok(List.of(new SubscriptionDTO()));
    }
}
