package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Data
@NoArgsConstructor
@Log4j2
public class Video {
    private String id;
    private String title;
    private String previewURL;
    private String description;
    private DocumentReference channelRef;
    @Exclude
    private Channel channel;
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

    @Exclude
    public Channel getChannel() {
        Objects.requireNonNull(channelRef, "Канал не найден или удален");
        try {
            return this.channel != null
                    ? this.channel
                    : (this.channel = channelRef.get().get().toObject(Channel.class));
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Exclude
    public boolean isAvailable() {
        Objects.requireNonNull(getChannel(), "Канал не найден или удален");
        return !this.isDeleted && isVisible && this.channel.isActive();
    }

    @Exclude
    public boolean canEdit() {
        return getChannel().isOwner();
    }
}
