package ru.vsu.csf.group7.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.firebase.auth.UserRecord;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.vsu.csf.group7.entity.User;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id;
    private boolean enabled;
    @NotEmpty
    private String email;
    private String password;
    private List<SimpleGrantedAuthority> authorities;


    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
