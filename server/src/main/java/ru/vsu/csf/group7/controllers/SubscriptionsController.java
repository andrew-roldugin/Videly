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
import ru.vsu.csf.group7.entity.Subscription;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.SubscriptionService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin
@AllArgsConstructor
@Log4j2
public class SubscriptionsController implements ISubscriptionsAPI {

    private final SubscriptionService subscriptionService;

    @Override
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Object> getUserSubscriptions(int limit, int offset) {
        try {
            List<SubscriptionDTO> subscriptions = subscriptionService.getMySubscriptions(limit, offset).stream()
                    .map(SubscriptionDTO::fromSubscription)
                    .toList();

            return ResponseEntity.ok(subscriptions);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PostMapping(value = "/follow", produces = "application/json")
    public ResponseEntity<Object> subscribe(@RequestParam("channelId") String channelId) {
        try {
            Subscription subscription = subscriptionService.subscribe(channelId);
            return ResponseEntity.ok(SubscriptionDTO.fromSubscription(subscription));
        } catch (NullPointerException | NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка"));
        }
    }
}
