package ru.vsu.csf.group7.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.Video;

import java.util.List;

@Builder
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO {
    private String name;
    private String avatarURL, headerURL, about;
    private Timestamp createdAt;
    private List<Video> videos;

    public static ChannelDTO fromChannel(Channel c) {
        return ChannelDTO.builder()
                .name(c.getName())
                .avatarURL(c.getAvatarURL())
                .headerURL(c.getHeaderURL())
                .createdAt(c.getCreatedAt())
                .videos(c.getVideos())
                .build();
    }
}
