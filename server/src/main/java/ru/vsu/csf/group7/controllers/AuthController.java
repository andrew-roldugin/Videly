package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.Extensions;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.config.TokenProvider;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.exceptions.UserNotFoundException;
import ru.vsu.csf.group7.http.request.LoginRequest;
import ru.vsu.csf.group7.http.request.RefreshTokenRequest;
import ru.vsu.csf.group7.http.request.SignupRequest;
import ru.vsu.csf.group7.http.response.JWTTokenResponse;
import ru.vsu.csf.group7.http.response.JWTTokenSuccessResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.TokenService;
import ru.vsu.csf.group7.services.UserService;
import ru.vsu.csf.group7.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.net.http.HttpClient;
import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")

@Tag(name = "AuthController", description = "Аутентификация")
public class AuthController {

    private final TokenProvider provider;
    private final UserService userService;
    private final TokenService tokenService;
    private final ChannelService channelService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(TokenProvider provider, UserService userService, TokenService tokenService, ChannelService channelService, AuthenticationManager authenticationManager) {
        this.provider = provider;
        this.userService = userService;
        this.tokenService = tokenService;
        this.channelService = channelService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Авторизация прошла успешно", content = @Content(schema = @Schema(implementation = JWTTokenSuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Произошла ошибка при авторизации", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Произошла неизвестная ошибка при авторизации", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Процесс авторизации пользователя и получение для него пары токенов"
    )
    public ResponseEntity<Object> signIn(@RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            User userByEmail = userService.findUserByEmail(loginRequest.getLogin());
            if (!userByEmail.getPassword().equals(loginRequest.getPassword()))
                return ResponseEntity.badRequest().body("Неправильный пароль");
            JWTTokenResponse tokens = getTokens(userByEmail.getId());

            return ResponseEntity.ok(new JWTTokenSuccessResponse(true, "Авторизация прошла успешно", tokens));
        } catch (FirebaseAuthException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при авторизации\n" + e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при авторизации"));
        }
    }

    private JWTTokenResponse getTokens(String userId) throws FirebaseAuthException {
        String customToken = provider.generateToken(userId);

        JWTTokenResponse tokens = tokenService.getVerifiedIdToken(customToken);
        tokens.setCustomToken(customToken);
        return tokens;
    }

    private JWTTokenResponse login(SignupRequest signupRequest, String userId) throws FirebaseAuthException {

//        User principal = new User(signupRequest);
////        Authentication authentication = authenticationManager
////                .authenticate(new UsernamePasswordAuthenticationToken(
////                        principal,
////                        null,
////                        principal.getAuthorities()
////                ));
////
////        SecurityContextHolder.getContext().setAuthentication(authentication);
        return getTokens(userId);
    }

    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Регистрация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Регистрация прошла успешно", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Произошла ошибка при регистрации", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Произошла неизвестная ошибка при регистрации", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Процесс регистрации пользователя"
    )
    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            String userId = userService.createUser(signupRequest);
            JWTTokenResponse tokens = login(signupRequest, userId);
            return ResponseEntity.ok().body(new MessageResponse("Пользователь успешно создан", tokens));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при регистрации\n" + e.getMessage()));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при регистрации"));
        }
    }
}
