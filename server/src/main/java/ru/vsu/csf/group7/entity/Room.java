package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.Data;

import java.util.List;

@Data
public class Room {
    private String id;
    private DocumentReference authorRef;
    private String password;
    private List<User> participants;
    private Video video;
    private Timestamp creationTimestamp;
}
