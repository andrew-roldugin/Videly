package ru.vsu.csf.group7.http.request;

import com.google.cloud.firestore.DocumentReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class UpdateChannelRequest {
    @Schema(description = "Новое имя канала", required = true)
    private String name;

    @Schema(description = "Новое описание канала")
    private String about;

    @Schema(description = "Ссылка на новое изображение канала")
    private String avatarURL;

    @Schema(description = "Ссылка на новую шапку канала")
    private String headerURL;

    @Schema(description = "Разрешить комментирование")
    private boolean allowComments;

    @Schema(description = "Разрешить оценивать ролики")
    private boolean allowRating;
}
