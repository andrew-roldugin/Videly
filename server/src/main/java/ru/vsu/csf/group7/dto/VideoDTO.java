package ru.vsu.csf.group7.dto;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.vsu.csf.group7.entity.Channel;
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
    private int likes = 0;
    private boolean allowComments = true, allowRating = true;
    private String videoURL;
//    private List<CommentDTO> comments;
    private ChannelDTO channel;
    private boolean isVisible, isDeleted, canEdit;
    private Timestamp uploadTS;

    public static VideoDTO fromVideo(@NonNull Video v) {
        return VideoDTO.builder()
                .id(v.getId())
                .title(v.getTitle())
                .description(v.getDescription())
                .views(v.getViews())
                .likes(v.getLikes())
                .allowComments(v.isAllowComments())
                .allowRating(v.isAllowRating())
                .videoURL(v.getVideoURL())
                .previewURL(v.getPreviewURL())
                .isVisible(v.isVisible())
                .isDeleted(v.isDeleted())
                .uploadTS(v.getUploadTS())
                .channel(ChannelDTO.fromChannel(v.getChannel()))
                .canEdit(v.canEdit())
                .build();
    }
}
