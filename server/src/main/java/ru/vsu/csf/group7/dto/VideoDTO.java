package ru.vsu.csf.group7.dto;

import com.google.cloud.firestore.DocumentReference;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Comment;
import ru.vsu.csf.group7.entity.Video;

import java.util.List;

@Builder
@Data
public class VideoDTO {

    private String id;
    private String title;
    private String previewURL;
    private String description;
    private int views;
    private long length;
    private boolean allowComments = true, allowRating = true;
    private String videoURL;
    private List<CommentDTO> comments;

    public static VideoDTO fromVideo(Video v) {
        return VideoDTO.builder()

                .build();
    }
}
