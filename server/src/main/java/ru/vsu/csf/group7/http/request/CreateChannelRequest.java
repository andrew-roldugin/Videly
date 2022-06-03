package ru.vsu.csf.group7.http.request;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.group7.entity.Video;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateChannelRequest {
    @NotEmpty
    private String name;
    private String avatarURL;
    private String headerURL;
    private String about;
}
