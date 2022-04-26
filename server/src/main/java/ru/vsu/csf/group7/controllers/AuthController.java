package ru.vsu.csf.group7.controllers;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.config.TokenProvider;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.exceptions.UserNotFoundException;
import ru.vsu.csf.group7.http.request.LoginRequest;
import ru.vsu.csf.group7.http.request.SignupRequest;
import ru.vsu.csf.group7.http.response.JWTTokenResponse;
import ru.vsu.csf.group7.http.response.JWTTokenSuccessResponse;
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

    @Autowired
    public AuthController(TokenProvider provider, UserService userService, TokenService tokenService) {
        this.provider = provider;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
//        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            User userByEmail = userService.findUserByEmail(loginRequest.getLogin());
            if (!userByEmail.getPassword().equals(loginRequest.getPassword()))
                return ResponseEntity.badRequest().body("Неправильный пароль");
            String customToken = provider.generateToken(userByEmail);

            JWTTokenResponse tokens = tokenService.getVerifiedIdToken(customToken);
            tokens.setCustomToken(customToken);
            return ResponseEntity.ok(tokens);

//            return ResponseEntity.ok(new JWTTokenSuccessResponse(true, "Авторизация прошла успешно", token));
        } catch (FirebaseAuthException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при авторизации" + e.getLocalizedMessage());
        } catch (ExecutionException | InterruptedException  e) {
            return ResponseEntity.internalServerError().body("Произошла неизвестная ошибка при авторизации");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok("test");
    }

//
//    private String[] doLogin(LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(
//                        loginRequest.getLogin(),
//                        loginRequest.getPassword()
//                ));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new String[] {
//                SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(((User) authentication.getPrincipal()), null, null),
//                tokenService.createRefreshToken(loginRequest.getLogin()).getRefreshToken()
//        };
//    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            userService.createUser(signupRequest);
            //return ResponseEntity.ok().body(user.getUid());
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при регистрации\n" + e.getMessage()));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при регистрации"));
        }
        return ResponseEntity.ok().body(new MessageResponse("Пользователь успешно создан"));
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
