package ru.vsu.csf.group7.entity;

import com.google.cloud.firestore.DocumentReference;

import java.time.LocalDateTime;

public class History {
    private DocumentReference videoRef, userRef;
    private LocalDateTime ts;
}
