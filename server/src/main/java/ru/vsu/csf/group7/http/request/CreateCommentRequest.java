package ru.vsu.csf.group7.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema
public class CreateCommentRequest {
    @Schema(description = "Текст комментария")
    @NotEmpty(message = "Напишите что-нибудь...")
    private String content;

    @Schema(description = "Ссылка на видео, под которым будет написан комментарий")
    private String videoId;
}
