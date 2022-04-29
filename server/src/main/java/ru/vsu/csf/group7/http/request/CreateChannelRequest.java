package ru.vsu.csf.group7.http.request;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.group7.entity.Video;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateChannelRequest {
    public CreateChannelRequest(String name) {
        this.name = name;
    }

    private String name;
    private String avatarURL, headerURL, about;
    private LocalDateTime createdAt = LocalDateTime.now();
}
