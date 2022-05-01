package ru.vsu.csf.group7.dto;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private String id;
    private String content;
    private boolean isDeleted = false;
    private LocalDateTime writtenOn;
}
