package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.SetOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.History;
import ru.vsu.csf.group7.entity.Video;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@Service
public class HistoryService {
    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public HistoryService(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    public List<History> getByUserId(int limit, int offset) throws ExecutionException, InterruptedException {
        return userService.getUserRef()
                .collection("user_history")
                .limit(limit)
                .offset(offset)
                .get()
                .get()
                .getDocuments()
                .stream()
                .filter(queryDocumentSnapshot -> videoService.isVideoAvailable((DocumentReference) queryDocumentSnapshot.get("videoRef")))
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(History.class))
                .toList();
    }

    public History addToHistory(String videoId) throws ExecutionException, InterruptedException {
        DocumentReference videoReference = videoService.getVideoReference(videoId);
        DocumentReference user_history = userService.getUserRef()
                .collection("user_history")
                .document(videoId);
        user_history.set(new History(videoReference), SetOptions.merge());

        return user_history.get().get().toObject(History.class);
    }
}
