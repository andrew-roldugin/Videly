package ru.vsu.csf.group7.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.group7.entity.User;

@RestController
@CrossOrigin
@RequestMapping("/api/protected")
public class ProtectedCtrl {

    @GetMapping("/secret")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("hello from ProtectedCtrl " + ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
    }
}
