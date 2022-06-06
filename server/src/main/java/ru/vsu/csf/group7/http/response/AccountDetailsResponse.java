package ru.vsu.csf.group7.http.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.dto.ChannelDTO;
import ru.vsu.csf.group7.dto.UserDTO;

@Builder
@Data
@AllArgsConstructor
public class AccountDetailsResponse {
    private UserDTO userInfo;
    private ChannelDTO channelInfo;
}
