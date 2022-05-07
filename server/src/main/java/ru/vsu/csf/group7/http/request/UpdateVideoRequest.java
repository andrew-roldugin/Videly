package ru.vsu.csf.group7.http.request;

import com.google.cloud.firestore.DocumentReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.vsu.csf.group7.entity.Comment;

import java.util.List;

@Schema
@Data
public class UpdateVideoRequest implements Mappable {
    @Schema(description = "Новое название видео", required = true)
    private String title;

    @Schema(description = "Ссылка на новое превью видео")
    private String previewURL;

    @Schema(description = "Новое описание видео")
    private String description = "";

    @Schema(description = "Разрешить комментирование")
    private boolean allowComments = true;

    @Schema(description = "Разрешить оценивать ролики")
    private boolean allowRating = true;
}
