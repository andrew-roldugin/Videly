package ru.vsu.csf.group7.dto;

import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Video;

@Builder
@Data
public class VideoDTO {
    public static VideoDTO fromVideo(Video v) {
        return VideoDTO.builder()

                .build();
    }
}
