package ru.vsu.csf.group7.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class SearchVideoQuery {
    @Schema(description = "Название видео для поиска", required = true)
    private String title;

    @Schema(description = "На каком канале искать", required = true)
    private String channelId;

    int limit = 25, offset = 0;
}
