package ru.vsu.csf.group7.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdateCommentRequest {
    @Schema(description = "Обновленный текст комментария")
    private String content;

    @Schema(description = "ID видео")
    private String videoId;

    @Schema(description = "ID комментария")
    private String commentId;
}
