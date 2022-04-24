package ru.vsu.csf.group7.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/test")
@PreAuthorize("permitAll()")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("hello world");
    }
}
