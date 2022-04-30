package ru.vsu.csf.group7.dto;

import com.google.cloud.firestore.DocumentReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class SubscriptionDTO {

    @Schema(description = "Каналы на которые подписан пользователь")
    private List<ChannelDTO> channels;
}
