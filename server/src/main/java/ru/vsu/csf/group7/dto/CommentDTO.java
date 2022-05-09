package ru.vsu.csf.group7.dto;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.Builder;
import lombok.Data;
import ru.vsu.csf.group7.entity.Comment;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO {
    private String id;
    private String content;
    private boolean isDeleted;
    private Timestamp ts;

    public static CommentDTO fromComment(@Nonnull Comment comment){
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .isDeleted(comment.isDeleted())
                .ts(comment.getTs())
                .build();
    }
}
