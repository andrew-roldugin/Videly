package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IUserAPI;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.UpdateUserRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@AllArgsConstructor
@Log4j2
public class UserController implements IUserAPI {

    private final UserService userService;
    private final ChannelService channelService;

    @Override
    @GetMapping(value = "/",  produces = "application/json")
    public ResponseEntity<Object> myAccount(Principal principal) {
        try {
            User userByEmail = userService.getUserData(principal.getName());
//            if (userByEmail != null) {
//                AccountDetailsResponse response = new AccountDetailsResponse(UserDTO.fromUser(userByEmail), null);
//
//                Channel channel = userByEmail.getChannel();
//                if (channel != null) {
//                    response.setChannelInfo(ChannelDTO.fromChannel(channel));
//                }
//
//                return ResponseEntity.ok(response);
//            }
            return ResponseEntity.ok(UserDTO.fromUser(userByEmail));
        } catch (NotFoundException | NullPointerException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.internalServerError().body(new MessageResponse("Произошла ошибка при получении пользовательских данных"));
    }

    @Override
    @PreAuthorize("#userId.equals(authentication.principal.id.toString())")
    @PatchMapping(value = "/{userId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> updateUser(
            @Parameter(description = "ID обновляемого пользователя", required = true) @PathVariable("userId") String userId,
            @Valid @RequestBody UpdateUserRequest req,
            BindingResult bindingResult
    ) {
        try {
            return ResponseEntity.ok(UserDTO.fromUser(userService.updateUserById(req, userId)));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getLocalizedMessage()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse("При обновлении учетной записи произошла ошибка"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return ResponseEntity.ok(new MessageResponse("Данные обновлены"));
    }

//    @PreAuthorize("#userId.equals(authentication.principal.id.toString()) or hasRole('ROLE_ADMIN')")
    @Override
    @PreAuthorize("#userId.equals(authentication.principal.id)")
    @DeleteMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<MessageResponse> deleteUserAccount(@PathVariable("userId") String userId, @RequestParam(value = "fullDelete", required = false, defaultValue = "false") boolean fullDelete) {
        try {
            userService.removeUser(userId, fullDelete);
            return new ResponseEntity<>(new MessageResponse("Учетная запись успешно удалена"), HttpStatus.OK);
        } catch (FirebaseAuthException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse("При удалении учетной записи произошла ошибка " + e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse("При удалении учетной записи произошла ошибка"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/ban", produces = "application/json")
    public ResponseEntity<MessageResponse> banUser(@RequestParam("userId") String userId, @RequestParam(value = "banned", required = false, defaultValue = "false") boolean banned) {
        try {
            userService.banUser(userId, banned);
            return new ResponseEntity<>(new MessageResponse("Пользователь " + (banned ? "заблокирован" : "разблокирован")), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка"));
    }
}
