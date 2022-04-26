package ru.vsu.csf.group7.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.Video;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin
public class ViewHistoryController {

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<VideoDTO>> getViewsHistory(@PathVariable("userId") String userId) {

        return null;
    }

    @PostMapping("/history/{userId}")
    public ResponseEntity<?> addToHistory(@PathVariable("userId") String userId, String videoId) {

        return null;
    }
}
