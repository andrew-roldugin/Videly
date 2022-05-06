package ru.vsu.csf.group7.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.Timestamp;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.UserDetailsImpl;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id;
    private boolean isBanned, isAccountDeleted;
    @NotEmpty
    private String email;
//    private String password;
    private Timestamp createdAt;
    private List<?> authorities;

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isBanned(user.isBanned())
                .isAccountDeleted(user.isDeleted())
                .createdAt(user.getCreationTS())
                .authorities(Arrays.asList(user.getGrantedAuthorities().toArray()))
                .build();
    }
}
