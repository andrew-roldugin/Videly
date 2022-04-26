package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.http.request.UpdateUserRequest;
import ru.vsu.csf.group7.http.response.AccountDetailsResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@AllArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;
    private final ChannelService channelService;

    @GetMapping("/")
    public ResponseEntity<Object> myAccount(Principal principal) {
        try {
            User userByEmail = userService.findUserByEmail(principal.getName());
            if (userByEmail != null){
                Channel channel = channelService.getByUserId(userByEmail.getId());
                AccountDetailsResponse response = new AccountDetailsResponse(UserDTO.fromUser(userByEmail), ChannelDTO.fromChannel(channel));
                return ResponseEntity.ok(response);
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.internalServerError().body(new MessageResponse("Произошла ошибка при получении пользовательских данных"));
    }

    @PreAuthorize("#userId.equals(authentication.principal.id.toString())")
    @PatchMapping("/{userId}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequest req, BindingResult bindingResult) {
        final User user = userService.updateUserById(req, userId);
        return ResponseEntity.ok(new MessageResponse("Данные обновлены", UserDTO.fromUser(user)));
    }

//    @PreAuthorize("#userId.equals(authentication.principal.id.toString()) or hasRole('ROLE_ADMIN')")
    @PreAuthorize("#userId.equals(authentication.principal.id.toString())")
    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> deleteUserAccount(@PathVariable("userId") String userId) {
        userService.removeUser(userId);
        return new ResponseEntity<>(new MessageResponse("Учетная запись успешно удалена"), HttpStatus.OK);
    }
}
