package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
public class Video {
    private String id;
    private String title;
    private String previewURL;
    private String description;
    private DocumentReference channelRef;
    private int views = 0;
    private int likes = 0;
    private boolean allowComments = true, allowRating = true;
    private boolean isVisible = true, isDeleted = false;
    private String videoURL;
    private List<DocumentReference> commentsRefs;
    private Timestamp uploadTS = Timestamp.now();

    public Video(CreateVideoRequest request) {
        this.title = request.getTitle();
        this.previewURL = request.getPreviewURL();
        this.description = request.getDescription();
        this.videoURL = request.getVideoURL();
        this.allowComments = request.isAllowComments();
        this.allowRating = request.isAllowRating();
    }
}
