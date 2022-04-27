package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.config.TokenProvider;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.exceptions.UserNotFoundException;
import ru.vsu.csf.group7.http.request.LoginRequest;
import ru.vsu.csf.group7.http.request.SignupRequest;
import ru.vsu.csf.group7.http.response.JWTTokenResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.TokenService;
import ru.vsu.csf.group7.services.UserService;
import ru.vsu.csf.group7.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {

    private final TokenProvider provider;
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(TokenProvider provider, UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.provider = provider;
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
//        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            User userByEmail = userService.findUserByEmail(loginRequest.getLogin());
            if (!userByEmail.getPassword().equals(loginRequest.getPassword()))
                return ResponseEntity.badRequest().body("Неправильный пароль");
            JWTTokenResponse tokens = getTokens(userByEmail.getId());

            return ResponseEntity.ok(new MessageResponse("Авторизация прошла успешно", tokens));
        } catch (FirebaseAuthException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при авторизации" + e.getLocalizedMessage()));
        } catch (ExecutionException | InterruptedException e) {
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
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(
//                        signupRequest.getEmail(),
//                        signupRequest.getPassword()
//                ));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return getTokens(userId);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            String userId = userService.createUser(signupRequest);
            return ResponseEntity.ok().body(new MessageResponse("Пользователь успешно создан", login(signupRequest, userId)));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при регистрации\n" + e.getMessage()));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при регистрации"));
        }

    }

//    @PostMapping("/refresh")
//    public ResponseEntity<Object> refreshToken(@Valid @RequestBody RefreshTokenRequest req, BindingResult bindingResult) {
//        Optional<UserToken> t;
//        String refreshToken = req.getRefreshToken();
//        if ((t = tokenService.findByToken(refreshToken)).isPresent()) {
//            if (jwtTokenProvider.validateToken(refreshToken) && t.get().getRefreshToken().equals(refreshToken)) {
//                //final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                return ResponseEntity.ok(new JWTTokenResponse(new String[]{
//                        SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(t.get().getUser(), null, null),
//                        refreshToken
//                }));
//            }
//        }
//        throw new ApiException("Токен устарел");
//    }
}
