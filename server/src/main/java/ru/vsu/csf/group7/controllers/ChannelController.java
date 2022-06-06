package ru.vsu.csf.group7.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IChannelAPI;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.VideoService;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("/api/channel")
@AllArgsConstructor
@Log4j2
public class ChannelController implements IChannelAPI {
    private final ChannelService channelService;
    private final VideoService videoService;

    @Override
    @PostMapping(value = "/createNew", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> createNewChannel(@Valid @RequestBody CreateChannelRequest request) {
        try {
            Channel channel = channelService.create(request);
            return ResponseEntity.ok().body(ChannelDTO.fromChannel(channel));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Произошла ошибка при создании канала"));
        }
    }

//    @GetMapping(value = "/", produces = "application/json")
//    public ResponseEntity<Object> getByChannelId(@RequestParam("channelId") String channelId) {
//        return getBy( channelId, "");
//    }
//
//    @GetMapping(value = "/", produces = "application/json")
//    public ResponseEntity<Object> getByUserId(@RequestParam("userId") String userId) {
//        return getBy("", userId);
//    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Object> getBy(@RequestParam(value = "channelId", defaultValue = "") String channelId, @RequestParam(value = "userId") String userId) {
        try {
            Channel channel = !channelId.isEmpty() ? channelService.findByChannelId(channelId) : channelService.findByUserId(userId);
//            List<Video> videos = videoService.getAllVideosOnChannel(channelId, 25, 0);
//            channel.setVideos(videos);
            return ResponseEntity.ok(ChannelDTO.fromChannel(channel));
        } catch (NotFoundException | NullPointerException ex) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Ошибка при загрузке данных канала " + e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла внутренняя ошибка сервера"));
        }
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Object> getAll() {
        try {
            List<ChannelDTO> channels = channelService.getAll().stream()
                    .map(ChannelDTO::fromChannel)
                    .toList();

            return ResponseEntity.ok(channels);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Ошибка при загрузке данных канала " + e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла внутренняя ошибка сервера"));
        }
    }

    @Override
    @GetMapping(value = "/find", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> findByQuery(@Valid @RequestBody SearchChannelQuery query) {
        try {
            List<Channel> channels = channelService.findChannels(query);
            if (channels.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(channels.stream().map(ChannelDTO::fromChannel).toList());
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла внутренняя ошибка сервера"));
        }
    }

    @Override
    @PatchMapping(value = "/update/{channelId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> update(@PathVariable("channelId") String channelId, @RequestBody UpdateChannelRequest req) {
        try {
            Channel c = channelService.updateById(req, channelId);
            return ResponseEntity.ok(ChannelDTO.fromChannel(c));
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла ошибка при обновлении данных канала"));
        }
    }

    @Override
    @DeleteMapping(value = "/delete/{channelId}", produces = "application/json")
    public ResponseEntity<MessageResponse> delete(@PathVariable("channelId") String channelId, @RequestParam(value = "fullDelete", required = false, defaultValue = "false") boolean fullDelete) {
        try {
            channelService.deleteByChannelId(channelId, fullDelete);
            return ResponseEntity.ok(new MessageResponse("Канал успешно удален"));
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла ошибка при удалении канала"));
        }
    }
}
