package ru.vsu.csf.group7.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class RoomDTO {
    @Schema(description = "Пароль комнаты")
    private String password;

    @Schema(description = "Текущее видео")
    private VideoDTO video;

    @Schema(description = "Создатель комнаты")
    private UserDTO author;

    @Schema(description = "Подключенные участники комнаты")
    private List<ChannelDTO> participants;
}
