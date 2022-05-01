package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IHistoryAPI;
import ru.vsu.csf.group7.dto.VideoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin
public class HistoryController implements IHistoryAPI {

    @Override
    @GetMapping(value = "/history/{userId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<VideoDTO>> getViewsHistory(@PathVariable("userId") String userId) {

        return null;
    }

    @Override
    @PostMapping(value = "/history/{userId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addToHistory(@PathVariable("userId") String userId, String videoId) {

        return null;
    }
}
