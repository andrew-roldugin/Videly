package ru.vsu.csf.group7_1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;

import java.net.http.HttpClient;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
@AllArgsConstructor
public class AuthController {


//    private final JWTTokenProvider jwtTokenProvider;
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//    private final TokenService tokenService;
//
//
//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
//        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;
//
//        return ResponseEntity.ok(new JWTTokenResponse(doLogin(loginRequest)));
//    }
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
//
//    @PostMapping("/register")
//    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
//        final ResponseEntity<Object> errors = new ResponseErrorValidation().mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;
//        final User user = userService.createUser(signupRequest);
////        final LoginRequest loginRequest = new LoginRequest();
////        loginRequest.setLogin(user.getLogin());
////        loginRequest.setPassword(user.getPassword());
////        return ResponseEntity.ok(login(loginRequest, null));
//
//        return ResponseEntity.ok().body(new MessageResponse("Пользователь успешно создан"));
//    }
//
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
