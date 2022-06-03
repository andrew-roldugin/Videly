package ru.vsu.csf.group7.controllers.interfaces;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.http.response.MessageWithDataResponse;

//@Hidden

@Hidden
public interface ITestController {
    @GetMapping("/hello")
    @Operation(
            summary = "Подписаться на канал",
            security = @SecurityRequirement(name = "bearer_token"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    ResponseEntity<String> test();

    @GetMapping("/get")
    ResponseEntity<String> testGet();

    @PostMapping("/post")
    ResponseEntity<String> testPost(@RequestBody String body);

    @DeleteMapping("/delete")
    ResponseEntity<String> testDelete();

    @PatchMapping("/patch")
    ResponseEntity<String> testUpdate();
}
