package ru.vsu.csf.group7.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.Timestamp;
import com.google.firebase.auth.UserRecord;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.http.request.SignupRequest;

import javax.validation.constraints.NotEmpty;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id;
    private boolean enabled;
    @NotEmpty
    private String email;
    private String password;
    private Timestamp createdAt;
    private List<SimpleGrantedAuthority> authorities;


    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .build();
    }

    public static UserDTO fromUserRecord(SignupRequest req, UserRecord userRecord) {
//        long creationTimestamp = userRecord.getUserMetadata().getCreationTimestamp();
//        com.google.protobuf.Timestamp timestamp = com.google.protobuf.Timestamp.newBuilder().setSeconds(creationTimestamp).build();
//        Timestamp ts = Timestamp.fromProto(timestamp);
        return UserDTO.builder()
                .id(userRecord.getUid())
                .email(userRecord.getEmail())
                .enabled(!userRecord.isDisabled())
                .password(req.getPassword())
                //.createdAt(ts)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(req.getRole().name())))
                .build();
    }
}
