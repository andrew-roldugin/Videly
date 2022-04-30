package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;
import ru.vsu.csf.group7.http.request.SearchVideoQuery;
import ru.vsu.csf.group7.http.request.UpdateVideoRequest;
import ru.vsu.csf.group7.http.response.JWTTokenSuccessResponse;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.VideoService;

import java.io.File;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/video")
@AllArgsConstructor
@Tag(name = "VideoController", description = "Модуль обработки видео")
public class VideoController {

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

    @PostMapping("/uploadNew")
    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "Видео успешно загружено", content = @Content(schema = @Schema(implementation = VideoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            },
            summary = "Загрузка нового видео в хранилище"
    )
    public ResponseEntity<VideoDTO> uploadNewVideo(
            @Parameter(description = "Параметры для загрузки нового видео", required = true) @RequestBody CreateVideoRequest request
//            @Parameter(required = true, description = "Загружаемый видеофайл") @RequestParam("video") MultipartFile video,
//            @RequestParam("preview") MultipartFile preview
    ){
        Video v = videoService.create(request);
        return ResponseEntity.ok(VideoDTO.fromVideo(v));
    }

    @GetMapping(value = "/{videoId}", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Загрузка данных о видео",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные загружены", content = @Content(schema = @Schema(implementation = VideoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Видео не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            },
            description = "Загрузка данных, когда пользователь открывает какое-либо видео\n" +
                    "А также обновление количества просмотров"
    )
    public ResponseEntity<VideoDTO> loadVideo(@Parameter(description = "ID загружаемого видео", required = true) @PathVariable("videoId") String videoId){
        return ResponseEntity.ok(VideoDTO.fromVideo(videoService.findVideoById(videoId)));
    }

    @GetMapping(value = "/find", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Поиск видео по различным критериям",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Есть данные по запросу", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ничего не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<List<VideoDTO>> findByQuery(@RequestBody SearchVideoQuery searchVideoQuery){
        return ResponseEntity.ok(videoService.findVideos(searchVideoQuery).stream().map(VideoDTO::fromVideo).toList());
    }

    @PatchMapping(value = "/update/{videoId}", produces = {"application/json", "multipart/form-data"}, consumes = "application/json")
    @Operation(
            summary = "Обновление данных о видео",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные о видео успешно обновлены", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Видео не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> update(@Parameter(description = "ID обновляемого видео", required = true) @PathVariable("videoId") String videoId, @RequestBody UpdateVideoRequest req){
        Video v = videoService.updateVideoById(req, videoId);
        return ResponseEntity.ok(new MessageResponse("Данные о видео обновлены", VideoDTO.fromVideo(v)));
    }

    @DeleteMapping(value = "/delete/{videoId}", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Удаление видео",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Видео успешно удалено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Видео не найдено", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> delete(@Parameter(description = "ID удаляемого видео", required = true) @PathVariable("videoId") String videoId){
        videoService.deleteById(videoId);
        return ResponseEntity.ok(new MessageResponse("Видео успешно удалено"));
    }
}
