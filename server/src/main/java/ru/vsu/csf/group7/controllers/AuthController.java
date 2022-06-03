package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.config.TokenProvider;
import ru.vsu.csf.group7.controllers.interfaces.IAuthAPI;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.UserDetailsImpl;
import ru.vsu.csf.group7.exceptions.UserNotFoundException;
import ru.vsu.csf.group7.http.request.LoginRequest;
import ru.vsu.csf.group7.http.request.SignupRequest;
import ru.vsu.csf.group7.http.response.JWTTokenResponse;
import ru.vsu.csf.group7.http.response.JWTTokenSuccessResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.TokenService;
import ru.vsu.csf.group7.services.UserService;
import ru.vsu.csf.group7.validations.ResponseErrorValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")

public class AuthController implements IAuthAPI {

    private final TokenProvider provider;
    private final UserService userService;
    private final TokenService tokenService;
    private final ChannelService channelService;


    @Autowired
    public AuthController(TokenProvider provider, UserService userService, TokenService tokenService, ChannelService channelService) {
        this.provider = provider;
        this.userService = userService;
        this.tokenService = tokenService;
        this.channelService = channelService;
    }

    @Override
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> signIn(@RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            User userByEmail = userService.findUserByEmail(loginRequest.getEmail());
            if (!userByEmail.getPassword().equals(loginRequest.getPassword()))
                return ResponseEntity.badRequest().body(new MessageResponse("Неправильный пароль"));
            JWTTokenResponse tokens = getTokens(userByEmail.getId());

            return ResponseEntity.ok(new JWTTokenSuccessResponse(true, "Авторизация прошла успешно", tokens));
        } catch (FirebaseAuthException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при авторизации\n" + e.getLocalizedMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при авторизации"));
        }
    }

    @Override
    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            String userId = userService.createUser(signupRequest);
            JWTTokenResponse tokens = login(signupRequest, userId, request, response);
            return ResponseEntity.ok().body(tokens);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при регистрации\n" + e.getMessage()));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при регистрации"));
        }
    }

    private JWTTokenResponse getTokens(String userId) throws FirebaseAuthException {
        String customToken = provider.generateToken(userId);

        JWTTokenResponse tokens = tokenService.getVerifiedIdToken(customToken);
        tokens.setCustomToken(customToken);
        return tokens;
    }

    private JWTTokenResponse login(SignupRequest signupRequest, String userId, HttpServletRequest request, HttpServletResponse response) throws FirebaseAuthException {

        UserDetailsImpl principal = new UserDetailsImpl(signupRequest);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );
//        request.getSession();

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return getTokens(userId);
    }
}
