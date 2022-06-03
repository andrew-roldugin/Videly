package ru.vsu.csf.group7.dto;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.Subscription;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Data
@Schema
@Builder
public class SubscriptionDTO {
    private String id;
    private ChannelDTO channel;
    private Timestamp followedSince;

    public static SubscriptionDTO fromSubscription(@Nonnull Subscription subscription) {
        return SubscriptionDTO.builder()
                .channel(ChannelDTO.fromChannel(subscription.getChannel()))
                .id(subscription.getId())
                .followedSince(subscription.getFollowedSince())
                .build();
    }
//    @Schema(description = "Каналы на которые подписан пользователь")
//    private List<ChannelDTO> channels;
}
