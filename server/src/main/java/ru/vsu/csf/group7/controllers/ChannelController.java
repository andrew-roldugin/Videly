package ru.vsu.csf.group7.controllers;

import com.fasterxml.jackson.databind.util.ObjectBuffer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.dto.VideoDTO;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.http.request.*;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.ChannelService;
import ru.vsu.csf.group7.services.VideoService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("/api/channel")
@AllArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping("/createNew")
    public ResponseEntity<Object> createNewChannel(@RequestBody CreateChannelRequest request){
        Channel channel = null;
        try {
            channel = channelService.create(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при создании канала");
        }
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelDTO> load(@PathVariable("channelId") String channelId){
        return ResponseEntity.ok(ChannelDTO.fromChannel(channelService.findById(channelId)));
    }

    @GetMapping("/find")
    public ResponseEntity<List<ChannelDTO>> findByQuery(@RequestBody SearchChannelQuery q){
        return ResponseEntity.ok(channelService.findChannels(q).stream().map(ChannelDTO::fromChannel).toList());
    }

    @PatchMapping("/update/{channelId}")
    public ResponseEntity<MessageResponse> update(@PathVariable("channelId") String channelId, @RequestBody UpdateChannelRequest req){
        Channel c = channelService.updateById(req, channelId);
        return ResponseEntity.ok(new MessageResponse("Данные о канале обновлены", ChannelDTO.fromChannel(c)));
    }

    @DeleteMapping("/delete/{channelId}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("channelId") String channelId){
        channelService.deleteById(channelId);
        return ResponseEntity.ok(new MessageResponse("Канал успешно удален"));
    }
}
