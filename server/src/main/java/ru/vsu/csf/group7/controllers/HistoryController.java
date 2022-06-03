package ru.vsu.csf.group7.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.IHistoryAPI;
import ru.vsu.csf.group7.dto.HistoryDTO;
import ru.vsu.csf.group7.entity.History;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.HistoryService;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/history")
@CrossOrigin
@Log4j2
public class HistoryController implements IHistoryAPI {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    @GetMapping(value = "/history", produces = "application/json")
    public ResponseEntity<Object> getViewsHistory(@RequestParam(value = "limit", defaultValue = "25") int limit, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        try {
            List<HistoryDTO> history = historyService.getByUserId(limit, offset).stream()
                    .map(HistoryDTO::fromHistory)
                    .toList();
            return ResponseEntity.ok().body(history);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла ошибка при загрузке истории просмотров"));
        }
    }

    @Override
    @PostMapping(value = "/history", produces = "application/json")
    public ResponseEntity<Object> addToHistory(@RequestParam("videoId") String videoId) {
        try {
            historyService.addToHistory(videoId);
            return ResponseEntity.ok().body(new MessageResponse("Успешно"));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла ошибка"));
        }
    }
}
