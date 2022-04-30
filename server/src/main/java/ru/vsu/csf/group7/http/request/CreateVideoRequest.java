package ru.vsu.csf.group7.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class CreateVideoRequest {

    @Schema(required = true, name = "Название видео")
    private String title;

    @Schema(name = "Ссылка на изображение")
    private String previewURL;

    @Schema(name = "Описание к видео")
    private String description;

    @Schema(name = "Разрешить оценивать видео")
    private boolean allowRating = true;

    @Schema(name = "Разрешить комментировать видео")
    private boolean allowComments = true;

    @Schema(name = "Ссылка на канал, куда загружается видео")
    private String channelId;
}
