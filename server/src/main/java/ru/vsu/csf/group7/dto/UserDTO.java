package ru.vsu.csf.group7.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.ERole;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.UserDetailsImpl;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id;
    private boolean isBanned, isAccountDeleted;
    @NotEmpty
    private String email;
    private Timestamp createdAt;
    private ERole role;
    private ChannelDTO channel;

    public static UserDTO fromUser(User user) {
        Objects.requireNonNull(user, "Учетная запись не найдена");
        UserDTOBuilder builder = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isBanned(user.isBanned())
                .isAccountDeleted(user.isDeleted())
                .createdAt(user.getCreatedAt())
                .role(user.getRole());
        if (user.getChannel() != null)
            builder.channel(ChannelDTO.fromChannel(user.getChannel()));

        return builder
                .build();
    }
}
