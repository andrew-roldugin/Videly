package ru.vsu.csf.group7.dto;

import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Channel;

@Builder
@Data
public class ChannelDTO {

    public static ChannelDTO fromChannel(Channel c) {
        return ChannelDTO.builder()

                .build();
    }
}
