package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.exceptions.UserNotFoundException;
import ru.vsu.csf.group7.http.request.UpdateUserRequest;
import ru.vsu.csf.group7.http.response.AccountDetailsResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.UserService;

import javax.lang.model.type.UnionType;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@AllArgsConstructor
@Log4j2
@Tag(name = "UserController", description = "Модуль управления аккаунтами пользователей")
public class UserController {

    private final UserService userService;
    private final ChannelService channelService;

    @GetMapping(value = "/",  produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Загрузка данных об аккаунте пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<Object> myAccount(Principal principal) {
        try {
            User userByEmail = userService.getUserData(principal.getName());
            if (userByEmail != null) {
                //Channel channel = channelService.getByRef(userByEmail.getId());
                AccountDetailsResponse response = new AccountDetailsResponse(UserDTO.fromUser(userByEmail), ChannelDTO.fromChannel(userByEmail.getChannel()));
                return ResponseEntity.ok(response);
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.internalServerError().body(new MessageResponse("Произошла ошибка при получении пользовательских данных"));
    }

    @PreAuthorize("#userId.equals(authentication.principal.id.toString())")
    @PatchMapping(value = "/{userId}",  produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Обновление пользовательских данных",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные обновлены", content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> updateUser(
            @Parameter(description = "ID обновляемого пользователя", required = true) @PathVariable("userId") String userId,
            @Valid @RequestBody UpdateUserRequest req,
            BindingResult bindingResult
    ) {
        try {
            userService.updateUserById(req, userId);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getLocalizedMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("Данные обновлены"));
    }

//    @PreAuthorize("#userId.equals(authentication.principal.id.toString()) or hasRole('ROLE_ADMIN')")
    @PreAuthorize("#userId.equals(authentication.principal.id.toString())")
    @DeleteMapping(value = "/{userId}",  produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Удаление аккаунта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> deleteUserAccount(@Parameter(description = "ID удаляемого аккаунта", required = true) @PathVariable("userId") String userId) {
        try {
            userService.removeUser(userId);
            return new ResponseEntity<>(new MessageResponse("Учетная запись успешно удалена"), HttpStatus.OK);
        } catch (FirebaseAuthException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse("При удалении учетной записи произошла ошибка " + e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse("При удалении учетной записи произошла ошибка"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
