package ru.vsu.csf.group7.entity;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class History {
    private UserDetailsImpl user;
    private List<Video> videos;
    private DocumentReference videoRef, userRef;
    private LocalDateTime ts;
}
