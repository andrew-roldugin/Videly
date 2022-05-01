package ru.vsu.csf.group7.dto;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;
import ru.vsu.csf.group7.entity.Video;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HistoryDTO {
    private List<VideoDTO> videos;
    private LocalDateTime ts;
}
