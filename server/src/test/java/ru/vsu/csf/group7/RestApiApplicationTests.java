package ru.vsu.csf.group7;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.services.VideoService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class RestApiApplicationTests {

    private final VideoService videoService;

    @Autowired
    RestApiApplicationTests(VideoService videoService) {
        this.videoService = videoService;
    }

    @Test
    void contextLoads() {
        FirebaseInit.initialize();
        Firestore db = FirestoreClient.getFirestore();



        try {
//            DocumentSnapshot snapshot = db.collection("channels")
//                    .document("2mry2RNovwWu3ntY2KHC")
//                    .get()
//                    .get();
//
//            Object videos = snapshot.get("videos");
//            Channel channel = snapshot.toObject(Channel.class);

            Object videoList = videoService.getAllVideosOnChannel("ndeP2aIsp9Sxul7MNkI8", 25, 0);
            System.out.printf("");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
