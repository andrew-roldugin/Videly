package ru.vsu.csf.group7.http.request;

import com.google.cloud.firestore.DocumentReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private boolean allowComments = true;

    @Schema(description = "Разрешить оценивать ролики")
    private boolean allowRating = true;

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return map;
    }
}
