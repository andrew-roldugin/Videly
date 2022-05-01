package ru.vsu.csf.group7.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.ISubscriptionsAPI;
import ru.vsu.csf.group7.dto.SubscriptionDTO;
import ru.vsu.csf.group7.http.response.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin
@AllArgsConstructor
@Log4j2
public class SubscriptionsController implements ISubscriptionsAPI {

    @Override
    @GetMapping(value = "/{userId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> getUserSubscriptions(@PathVariable("userId") String userId, BindingResult bindingResult) {
        return ResponseEntity.ok(List.of(new SubscriptionDTO()));
    }

    @Override
    @PostMapping(value = "/{userId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> subscribe(@PathVariable("userId") String userId, @RequestParam("channelId") String channelId, BindingResult bindingResult) {

        return ResponseEntity.ok(List.of(new SubscriptionDTO()));
    }
}
