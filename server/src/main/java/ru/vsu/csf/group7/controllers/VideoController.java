package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IVideoAPI;
import ru.vsu.csf.group7.dto.VideoDTO;
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
public class VideoController implements IVideoAPI {

    private final VideoService videoService;

//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    @ResponseBody
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String uploadDir = "/uploads/";
//            String realPath = ""; // request.getServletContext().getRealPath(uploadDir);
//
//            File transferFile = new File(realPath + "/" + file.getOriginalFilename());
//            file.transferTo(transferFile);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return "Failure";
//        }
//
//        return "Success";
//    }

    @Override
    @PostMapping("/uploadNew")
    public ResponseEntity<Object> uploadNewVideo(
            @Parameter(description = "Параметры для загрузки нового видео", required = true) @RequestBody CreateVideoRequest request){
        Video v = videoService.create(request);
        return ResponseEntity.ok(VideoDTO.fromVideo(v));
    }

    @Override
    @GetMapping(value = "/{videoId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<VideoDTO> loadVideo(@Parameter(description = "ID загружаемого видео", required = true) @PathVariable("videoId") String videoId){
        return ResponseEntity.ok(VideoDTO.fromVideo(videoService.findVideoById(videoId)));
    }

    @Override
    @GetMapping(value = "/find", produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<VideoDTO>> findByQuery(@RequestBody SearchVideoQuery searchVideoQuery){
        return ResponseEntity.ok(videoService.findVideos(searchVideoQuery).stream().map(VideoDTO::fromVideo).toList());
    }

    @Override
    @PatchMapping(value = "/update/{videoId}", produces = {"application/json", "multipart/form-data"}, consumes = "application/json")
    public ResponseEntity<Object> update(@Parameter(description = "ID обновляемого видео", required = true) @PathVariable("videoId") String videoId, @RequestBody UpdateVideoRequest req){
        Video v = videoService.updateVideoById(req, videoId);
        return ResponseEntity.ok(VideoDTO.fromVideo(v));
    }

    @Override
    @DeleteMapping(value = "/delete/{videoId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> delete(@Parameter(description = "ID удаляемого видео", required = true) @PathVariable("videoId") String videoId){
        videoService.deleteById(videoId);
        return ResponseEntity.ok(new MessageResponse("Видео успешно удалено"));
    }
}
