package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.ITestController;
import ru.vsu.csf.group7.http.response.MessageWithDataResponse;

@RestController
@CrossOrigin
@RequestMapping("/api/test")
@PreAuthorize("permitAll()")
public class TestController implements ITestController {

    @Override
    @GetMapping("/hello")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("hello world");
    }


    @Override
    @GetMapping("/get")
    public ResponseEntity<String> testGet() {
        return ResponseEntity.ok().body("testGet");
    }


    @Override
    @PostMapping("/post")
    public ResponseEntity<String> testPost(@RequestBody String body) {
        return ResponseEntity.ok().body("testPost");
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> testDelete() {
        return ResponseEntity.ok().body("testDelete");
    }

    @Override
    @PatchMapping("/patch")
    public ResponseEntity<String> testUpdate() {
        return ResponseEntity.ok().body("testUpdate123");
    }
}
