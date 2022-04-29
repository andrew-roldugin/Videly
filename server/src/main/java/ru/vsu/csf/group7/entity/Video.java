package ru.vsu.csf.group7.entity;

import com.google.cloud.firestore.DocumentReference;

import java.net.URI;
import java.util.List;

public class Video {
    private String name;
    private DocumentReference channelRef;
    private String previewURL;
    private String description;
    private int views;
    //private long length;
    private boolean allowComments = true, allowRating = true;
    private String videoURL;
    private List<DocumentReference> commentsRefs;
}
