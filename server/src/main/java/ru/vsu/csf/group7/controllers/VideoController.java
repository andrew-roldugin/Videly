package ru.vsu.csf.group7.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;
import ru.vsu.csf.group7.http.request.SearchVideoQuery;
import ru.vsu.csf.group7.http.request.UpdateVideoRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.VideoService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/video")
@AllArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/uploadNew")
    public ResponseEntity<VideoDTO> uploadNewVideo(@RequestBody CreateVideoRequest request){
        Video v = videoService.create(request);
        return ResponseEntity.ok(VideoDTO.fromVideo(v));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> loadVideo(@PathVariable("videoId") String videoId){
        return ResponseEntity.ok(videoService.findVideoById(videoId));
    }

    @GetMapping("/find")
    public ResponseEntity<List<VideoDTO>> findByQuery(@RequestBody SearchVideoQuery q){
        return ResponseEntity.ok(videoService.findVideos(q).stream().map(VideoDTO::fromVideo).toList());
    }

    @PatchMapping("/update/{videoId}")
    public ResponseEntity<MessageResponse> update(@PathVariable("videoId") String videoId, @RequestBody UpdateVideoRequest req){
        Video v = videoService.updateVideoById(req, videoId);
        return ResponseEntity.ok(new MessageResponse("Данные о видео обновлены", VideoDTO.fromVideo(v)));
    }

    @DeleteMapping("/delete/{videoId}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("videoId") String videoId){
        videoService.deleteById(videoId);
        return ResponseEntity.ok(new MessageResponse("Видео успешно удалено"));
    }
}
