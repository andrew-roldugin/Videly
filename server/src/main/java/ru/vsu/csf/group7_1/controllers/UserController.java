package ru.vsu.csf.group7_1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@AllArgsConstructor
public class UserController {

//    private final UserService userService;
//    private final MessageService messageService;
////    private final SubscribeService subscribeService;
//
//    @GetMapping("/all")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        final List<UserDTO> allUsers = userService.getAllUsers().stream()
//                .map(UserDTO::fromUser)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(allUsers);
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDTO> loadProfile(@PathVariable("userId") String userId) {
//        final Long id = Long.valueOf(userId);
//        final User user = userService.getUserById(id);
//        final UserDTO userDTO = UserDTO.fromUser(user);
//        userDTO.setMessagesCount(messageService.countUserMessages(id));
//        return ResponseEntity.ok(userDTO);
//    }
//
//    @GetMapping("/")
//    public ResponseEntity<Object> myAccount(Principal principal) {
//        final User user = userService.getCurrentUser(principal);
//        Long userId = user.getId();
//        UserDTO userDTO = UserDTO.fromUser(user);
//        userDTO.setMessagesCount(messageService.countUserMessages(userId));
//        return ResponseEntity.ok(userDTO);
//    }
//
//    @GetMapping("/count")
//    public ResponseEntity<MessageResponse> getCountOfUsers() {
//        return ResponseEntity.ok(new MessageResponse(userService.countUsers() + ""));
//    }
//
//    @GetMapping("/last")
//    public ResponseEntity<UserDTO> getLastUser() {
//        final Optional<User> lastUser = userService.getLastUser();
//        if (lastUser.isPresent()) {
//            final User user = lastUser.get();
//            return ResponseEntity.ok(UserDTO.fromUser(user));
//        }
//        return ResponseEntity.noContent().build();
//    }
//
//    @PreAuthorize("#userId.equals(authentication.principal.id.toString()) or hasRole('ROLE_ADMIN')")
//    @PatchMapping("/{userId}")
//    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequest req, BindingResult bindingResult) {
//        final Long id = Long.valueOf(userId);
//        final User user = userService.updateUserById(req, id);
//        return ResponseEntity.ok(UserDTO.fromUser(user));
//    }
//
//    @PreAuthorize("#userId.equals(authentication.principal.id.toString()) or hasRole('ROLE_ADMIN')")
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<MessageResponse> deleteUserAccount(@PathVariable("userId") String userId) {
//        userService.removeUser(Long.valueOf(userId));
//        return new ResponseEntity<>(new MessageResponse("Учетная запись успешно удалена"), HttpStatus.OK);
//    }
}
