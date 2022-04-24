package ru.vsu.csf.group7_1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/test/")
@PreAuthorize("permitAll()")
public class TestController {

    @GetMapping("/hello")
    public String test(){
        return "hello world";
    }
}
