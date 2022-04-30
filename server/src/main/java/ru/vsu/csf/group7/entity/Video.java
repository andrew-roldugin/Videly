package ru.vsu.csf.group7.entity;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
public class Video {
    private String id;
    private String title;
    private String previewURL;
    private String description;
    private DocumentReference channelRef;
    private int views;
    private long length;
    private boolean allowComments = true, allowRating = true;
    private String videoURL;
    private List<DocumentReference> commentsRefs;
    private List<Comment> comments;
}
