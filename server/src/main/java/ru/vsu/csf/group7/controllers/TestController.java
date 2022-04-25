package ru.vsu.csf.group7.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/test")
@PreAuthorize("permitAll()")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("hello world");
    }


    @GetMapping("/get")
    public ResponseEntity<String> testGet() {
        return ResponseEntity.ok().body("testGet");
    }


    @PostMapping("/post")
    public ResponseEntity<String> testPost(@RequestBody String body) {
        return ResponseEntity.ok().body("testPost");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> testDelete() {
        return ResponseEntity.ok().body("testDelete");
    }

    @PatchMapping("/patch")
    public ResponseEntity<String> testUpdate() {
        return ResponseEntity.ok().body("testUpdate123");
    }
}
