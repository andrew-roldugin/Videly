package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IRoomAPI;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
@AllArgsConstructor
@Log4j2
public class RoomController implements IRoomAPI {

    @Override
    @GetMapping(value = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> create(@RequestParam("userId") String userId, BindingResult bindingResult) {

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(value = "/{roomId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> close(@PathVariable("roomId") String roomId, BindingResult bindingResult) {

        return ResponseEntity.noContent().build();
    }
}
