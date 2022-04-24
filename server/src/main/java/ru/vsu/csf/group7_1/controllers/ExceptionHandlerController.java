package ru.vsu.csf.group7_1.controllers;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ApiException.class)
//    public ResponseEntity<?> handleException(ApiException e) {
//        log.error("Произошла ошибка:\n" + e.getMessage());
//        return ResponseEntity
//                .badRequest()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(new Gson().toJson(new MessageResponse(e.getMessage().split("\n"))));
//    }
}
