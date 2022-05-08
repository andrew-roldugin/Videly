package ru.vsu.csf.group7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Data
@NoArgsConstructor
public class History {
    public History(DocumentReference videoRef) {
        this.videoRef = videoRef;
        this.ts = Timestamp.now();
    }

    private DocumentReference videoRef;
    private Timestamp ts;

    @Exclude
    public Video getVideo() throws ExecutionException, InterruptedException {
        return videoRef.get().get().toObject(Video.class);
    }
}
