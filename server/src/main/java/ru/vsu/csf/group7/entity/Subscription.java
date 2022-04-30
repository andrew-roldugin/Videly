package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.Data;

@Data
public class Subscription {
    private String id;
    private DocumentReference userRef, channelRef;
    private Timestamp followedSince;
}
