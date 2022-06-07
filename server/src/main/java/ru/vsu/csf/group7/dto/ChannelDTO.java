package ru.vsu.csf.group7.dto;

import com.google.cloud.Timestamp;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Channel;

import java.util.List;
import java.util.Objects;

@Builder
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO {
    private String id;
    private String name;
    private String avatarURL, headerURL, about;
    private Timestamp createdAt;
//    private List<VideoDTO> videos;
    private boolean allowComments = true, allowRating = true;
    private boolean isDeleted = false;
    private boolean canUploadVideo;

    public static ChannelDTO fromChannel(Channel c){
        Objects.requireNonNull(c, "Канал не найден или удален");
        ChannelDTOBuilder builder = ChannelDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .avatarURL(c.getAvatarURL())
                .headerURL(c.getHeaderURL())
                .createdAt(c.getCreatedAt())
                .about(c.getAbout())
                .isDeleted(c.isDeleted())
                .allowComments(c.isAllowComments())
                .allowRating(c.isAllowRating())
                .canUploadVideo(c.isOwner());

//        if (c.getVideos() != null)
//            builder.videos(c.getVideos().stream().map(VideoDTO::fromVideo).toList());

        return builder
                .build();
    }
}