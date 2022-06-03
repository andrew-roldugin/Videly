package ru.vsu.csf.group7.dto;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import ru.vsu.csf.group7.entity.History;
import ru.vsu.csf.group7.entity.Video;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Data
@Builder
@Log4j2
public class HistoryDTO {
    private VideoDTO video;
    private Timestamp ts;

    public static HistoryDTO fromHistory(@Nonnull History history) {
        try {
            return HistoryDTO.builder()
                    .ts(history.getTs())
                    .video(VideoDTO.fromVideo(history.getVideo()))
                    .build();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Ошибка при конвертации");
        }
        return null;
    }
}
