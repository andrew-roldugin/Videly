package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IVideoAPI;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;
import ru.vsu.csf.group7.http.request.SearchVideoQuery;
import ru.vsu.csf.group7.http.request.UpdateVideoRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.VideoService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("/api/video")
@AllArgsConstructor
@Log4j2
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
            @Parameter(description = "Параметры для загрузки нового видео", required = true) @RequestBody CreateVideoRequest request) {
        try {
            Video v = videoService.create(request);
            return ResponseEntity.ok(VideoDTO.fromVideo(v));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при загрузке видео на сервер"));
        } catch (NullPointerException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping(value = "/{videoId}", produces = "application/json")
    public ResponseEntity<Object> loadVideo(@PathVariable("videoId") String videoId) {
        try {
            return ResponseEntity.ok(VideoDTO.fromVideo(videoService.findVideoById(videoId)));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при загрузке видео"));
        } catch (NullPointerException | NotFoundException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping(value = "/find", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> findByQuery(@RequestBody SearchVideoQuery searchVideoQuery) {
        try {
            List<VideoDTO> body = videoService.findVideos(searchVideoQuery).stream()
                    .map(VideoDTO::fromVideo)
                    .toList();
            return ResponseEntity.ok(body);
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при поиске видео"));
        } catch (NullPointerException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/like", produces = "application/json")
    public ResponseEntity<Object> likeVideo(@RequestParam("videoId") String videoId) {
        try {
            videoService.updateRating(videoId);
            return ResponseEntity.ok(new MessageResponse("Успешно"));
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка"));
        } catch (NullPointerException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PatchMapping(value = "/update/{videoId}", produces = {"application/json"}, consumes = "application/json")
    public ResponseEntity<Object> update(@PathVariable("videoId") String videoId, @RequestBody UpdateVideoRequest req) {
        try {
            Video v = videoService.updateVideoById(req, videoId);
            return ResponseEntity.ok(VideoDTO.fromVideo(v));
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при обновлении видео"));
        } catch (NullPointerException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping(value = "/delete/{videoId}", produces = "application/json")
    public ResponseEntity<MessageResponse> delete(@PathVariable("videoId") String videoId, @RequestParam(value = "fullDelete", required = false, defaultValue = "false") boolean fullDelete) {
        try {
            videoService.deleteById(videoId, fullDelete);
            return ResponseEntity.ok(new MessageResponse("Видео успешно удалено"));
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка при удалении видео"));
        }
    }
}
