package ru.vsu.csf.group7.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IChannelAPI;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;

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

    @Override
    @PostMapping(value = "/createNew", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> createNewChannel(@Valid @RequestBody CreateChannelRequest request) {
        Channel channel = null;
        try {
            channel = channelService.create(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при создании канала");
        }
        return ResponseEntity.ok().body(ChannelDTO.fromChannel(channel));
    }

    @Override
    @GetMapping(value = "/{channelId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> load(@PathVariable("channelId") String channelId) {
        try {
            return ResponseEntity.ok(ChannelDTO.fromChannel(channelService.findById(channelId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Ошибка при загрузке данныз канала " + e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла внутрення ошибка сервера"));
        }
    }

    @Override
    @GetMapping(value = "/find", produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<ChannelDTO>> findByQuery(@Valid @RequestBody SearchChannelQuery query) {
        return ResponseEntity.ok(channelService.findChannels(query).stream().map(ChannelDTO::fromChannel).toList());
    }

    @Override
    @PatchMapping(value = "/update/{channelId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> update(@PathVariable("channelId") String channelId, @RequestBody UpdateChannelRequest req) {
        Channel c = channelService.updateById(req, channelId);
        return ResponseEntity.ok(ChannelDTO.fromChannel(c));
    }

    @Override
    @DeleteMapping(value = "/delete/{channelId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> delete(@PathVariable("channelId") String channelId) {
        channelService.deleteById(channelId);
        return ResponseEntity.ok(new MessageResponse("Канал успешно удален"));
    }
}
