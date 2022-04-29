package ru.vsu.csf.group7.entity;

import com.google.cloud.firestore.DocumentReference;

import java.time.LocalDateTime;

public class Comment {

    private DocumentReference userRef, VideoRef;
    private String content;
    private LocalDateTime ts;
}
