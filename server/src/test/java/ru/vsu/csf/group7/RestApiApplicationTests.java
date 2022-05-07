package ru.vsu.csf.group7;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vsu.csf.group7.entity.Channel;

import java.util.concurrent.ExecutionException;

@SpringBootTest
class RestApiApplicationTests {

    @Test
    void contextLoads() {
        FirebaseInit.initialize();
        Firestore db = FirestoreClient.getFirestore();
        try {
            DocumentSnapshot snapshot = db.collection("channels")
                    .document("2mry2RNovwWu3ntY2KHC")
                    .get()
                    .get();

            Object videos = snapshot.get("videos");
            Channel channel = snapshot.toObject(Channel.class);
            System.out.printf("");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
